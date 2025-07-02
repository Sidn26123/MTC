// filterStore.js
import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
import { immer } from 'zustand/middleware/immer';

// Cấu trúc state ban đầu
const initialState = {
    // Các nhóm filter với item được lấy từ server có dạng {id, name} + value để lưu trạng thái đã chọn
    personalityFilters: {
        items: [], // [{id: '1', name: 'Extrovert', value: false}, {id: '2', name: 'Introvert', value: false}, ...]
        loading: false,
        error: null,
    },
    timeFilters: {
        items: [], // [{id: '1', name: 'Morning', value: false}, {id: '2', name: 'Afternoon', value: false}, ...]
        loading: false,
        error: null,
    },
    statusFilters: {
        items: [], // [{id: '1', name: 'Active', value: true}, {id: '2', name: 'Inactive', value: false}, ...]
        loading: false,
        error: null,
    },
    dateRange: {
        startDate: null,
        endDate: null,
    },
    searchQuery: '',
    sortBy: 'createdAt',
    sortDirection: 'desc',
};

// Tạo một lớp trừu tượng cho filter
const createFilterSlice = (set, get) => ({
    ...initialState,

    // ========== ACTIONS CHUNG CHO TẤT CẢ CÁC NHÓM FILTER ==========

    // Fetch dữ liệu filter từ server
    fetchFilterOptions: async (filterType, apiEndpoint) => {
        // Đánh dấu là đang loading
        set(
            (state) => {
                state[filterType].loading = true;
                state[filterType].error = null;
            },
            false,
            `filters/fetch${filterType}Start`
        );

        try {
            // Thực hiện fetch dữ liệu từ API
            const response = await fetch(apiEndpoint);
            if (!response.ok) {
                throw new Error(`Failed to fetch ${filterType}`);
            }

            const data = await response.json();

            // Map dữ liệu từ server, thêm trường value mặc định là false
            const items = data.map(item => ({
                id: item.id,
                name: item.name,
                value: false, // Giá trị mặc định cho filter
            }));

            // Cập nhật state với dữ liệu mới
            set(
                (state) => {
                    state[filterType].items = items;
                    state[filterType].loading = false;
                },
                false,
                `filters/fetch${filterType}Success`
            );
        } catch (error) {
            set(
                (state) => {
                    state[filterType].error = error.message;
                    state[filterType].loading = false;
                },
                false,
                `filters/fetch${filterType}Error`
            );
        }
    },

    // Cập nhật giá trị của một filter item
    toggleFilterItem: (filterType, itemId) => {
        set(
            (state) => {
                const itemIndex = state[filterType].items.findIndex(item => item.id === itemId);
                if (itemIndex !== -1) {
                    state[filterType].items[itemIndex].value = !state[filterType].items[itemIndex].value;
                }
            },
            false,
            `filters/toggle${filterType}Item`
        );
    },

    // Set giá trị cho một filter item cụ thể
    setFilterItemValue: (filterType, itemId, value) => {
        set(
            (state) => {
                const itemIndex = state[filterType].items.findIndex(item => item.id === itemId);
                if (itemIndex !== -1) {
                    state[filterType].items[itemIndex].value = value;
                }
            },
            false,
            `filters/set${filterType}ItemValue`
        );
    },

    // Set tất cả các item trong một nhóm filter về một giá trị
    setAllFilterItems: (filterType, value) => {
        set(
            (state) => {
                state[filterType].items.forEach(item => {
                    item.value = value;
                });
            },
            false,
            `filters/setAll${filterType}Items`
        );
    },

    // ========== ACTIONS CỤ THỂ CHO TỪNG LOẠI FILTER ==========

    // Actions cho personalityFilters
    fetchPersonalityFilters: async (apiEndpoint = '/api/personality-filters') => {
        return get().fetchFilterOptions('personalityFilters', apiEndpoint);
    },

    togglePersonalityFilter: (itemId) => {
        get().toggleFilterItem('personalityFilters', itemId);
    },

    setAllPersonalityFilters: (value) => {
        get().setAllFilterItems('personalityFilters', value);
    },

    // Actions cho timeFilters
    fetchTimeFilters: async (apiEndpoint = '/api/time-filters') => {
        return get().fetchFilterOptions('timeFilters', apiEndpoint);
    },

    toggleTimeFilter: (itemId) => {
        get().toggleFilterItem('timeFilters', itemId);
    },

    setAllTimeFilters: (value) => {
        get().setAllFilterItems('timeFilters', value);
    },

    // Actions cho statusFilters
    fetchStatusFilters: async (apiEndpoint = '/api/status-filters') => {
        return get().fetchFilterOptions('statusFilters', apiEndpoint);
    },

    toggleStatusFilter: (itemId) => {
        get().toggleFilterItem('statusFilters', itemId);
    },

    setAllStatusFilters: (value) => {
        get().setAllFilterItems('statusFilters', value);
    },

    // Actions cho dateRange
    setDateRange: (startDate, endDate) => {
        set(
            (state) => {
                state.dateRange.startDate = startDate;
                state.dateRange.endDate = endDate;
            },
            false,
            'filters/setDateRange'
        );
    },

    // Action cho searchQuery
    setSearchQuery: (query) => {
        set(
            (state) => {
                state.searchQuery = query;
            },
            false,
            'filters/setSearchQuery'
        );
    },

    // Actions cho sorting
    setSortBy: (field) => {
        set(
            (state) => {
                if (state.sortBy === field) {
                    // Nếu đang sort theo field này rồi, đảo chiều sort
                    state.sortDirection = state.sortDirection === 'asc' ? 'desc' : 'asc';
                } else {
                    // Mặc định sort theo thứ tự giảm dần khi chọn field mới
                    state.sortBy = field;
                    state.sortDirection = 'desc';
                }
            },
            false,
            'filters/setSortBy'
        );
    },

    // Reset tất cả filters về giá trị mặc định
    resetAllFilters: () => {
        set(
            (state) => {
                // Reset các items về false nhưng giữ nguyên các items đã fetch từ server
                Object.keys(initialState).forEach(filterType => {
                    if (state[filterType] && state[filterType].items) {
                        state[filterType].items.forEach(item => {
                            item.value = false;
                        });
                    }
                });

                // Reset các giá trị khác
                state.dateRange = initialState.dateRange;
                state.searchQuery = initialState.searchQuery;
                state.sortBy = initialState.sortBy;
                state.sortDirection = initialState.sortDirection;
            },
            false,
            'filters/resetAllFilters'
        );
    },

    // Khởi tạo tất cả các filters (fetch dữ liệu từ server)
    initializeFilters: async () => {
        await Promise.all([
            get().fetchPersonalityFilters(),
            get().fetchTimeFilters(),
            get().fetchStatusFilters(),
        ]);
    },

    // Helper method để lấy ra số lượng filter đang được áp dụng
    getActiveFiltersCount: () => {
        const state = get();
        let count = 0;

        // Đếm các filter items đã được chọn
        const countActiveItems = (filterType) => {
            if (state[filterType] && state[filterType].items) {
                return state[filterType].items.filter(item => item.value).length;
            }
            return 0;
        };

        count += countActiveItems('personalityFilters');
        count += countActiveItems('timeFilters');
        count += countActiveItems('statusFilters');

        // Đếm date range
        if (state.dateRange.startDate || state.dateRange.endDate) count++;

        // Đếm search query
        if (state.searchQuery) count++;

        return count;
    },

    // Helper method để kiểm tra xem có filter nào đang được áp dụng không
    hasActiveFilters: () => {
        return get().getActiveFiltersCount() > 0;
    },

    // Helper method để lấy về tất cả các filter đang được áp dụng (để gửi lên server)
    getActiveFilters: () => {
        const state = get();
        const activeFilters = {};

        // Helper để lấy active items trong một filter type
        const getActiveItems = (filterType) => {
            if (state[filterType] && state[filterType].items) {
                return state[filterType].items
                    .filter(item => item.value)
                    .map(item => item.id);
            }
            return [];
        };

        // Lấy các filter đã được chọn
        const personalityIds = getActiveItems('personalityFilters');
        if (personalityIds.length > 0) {
            activeFilters.personalityIds = personalityIds;
        }

        const timeIds = getActiveItems('timeFilters');
        if (timeIds.length > 0) {
            activeFilters.timeIds = timeIds;
        }

        const statusIds = getActiveItems('statusFilters');
        if (statusIds.length > 0) {
            activeFilters.statusIds = statusIds;
        }

        // Lấy date range nếu có
        if (state.dateRange.startDate || state.dateRange.endDate) {
            activeFilters.dateRange = state.dateRange;
        }

        // Lấy search query nếu có
        if (state.searchQuery) {
            activeFilters.searchQuery = state.searchQuery;
        }

        // Lấy thông tin sắp xếp
        activeFilters.sortBy = state.sortBy;
        activeFilters.sortDirection = state.sortDirection;

        return activeFilters;
    },
});

// Factory function để tạo store mới (useful for testing or SSR)
export const createFilterStore = (initialState) =>
    create(
        devtools(
            persist(
                immer(
                    (set, get) => ({
                        ...createFilterSlice(set, get),
                        // Bạn có thể thêm các slice khác ở đây nếu cần
                        ...(initialState || {})
                    })
                ),
                {
                    name: 'filter-storage', // tên của storage key trong localStorage
                    partialize: (state) => ({
                        // Chỉ lưu các giá trị value của filter, không lưu trạng thái loading hay error
                        personalityFilters: {
                            items: state.personalityFilters.items.map(item => ({
                                id: item.id,
                                name: item.name,
                                value: item.value
                            }))
                        },
                        timeFilters: {
                            items: state.timeFilters.items.map(item => ({
                                id: item.id,
                                name: item.name,
                                value: item.value
                            }))
                        },
                        statusFilters: {
                            items: state.statusFilters.items.map(item => ({
                                id: item.id,
                                name: item.name,
                                value: item.value
                            }))
                        },
                        dateRange: state.dateRange,
                        sortBy: state.sortBy,
                        sortDirection: state.sortDirection,
                        // Không lưu searchQuery, vì thường không cần persist
                    }),
                }
            )
        )
    );

// Export default store instance
const useFilterStore = createFilterStore();

export default useFilterStore;