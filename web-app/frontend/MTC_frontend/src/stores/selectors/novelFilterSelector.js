import useFilterStore from '../novelFilterStore.js';

// Search
export const useSearchText = () =>
    useFilterStore((state) => state.searchText);
export const useSetSearchText = () =>
    useFilterStore((state) => state.setSearchText);

// Sort
export const useSortBy = () => useFilterStore((state) => state.sortBy);
export const useSetSortBy = () => useFilterStore((state) => state.setSortBy);

export const useSortOrder = () => useFilterStore((state) => state.sortOrder);
export const useSetSortOrder = () => useFilterStore((state) => state.setSortOrder);

// Filter Lists
export const useNovelProgressStatus = () => useFilterStore((state) => state.novelProgressStatus);
export const useSetNovelStatus = () => useFilterStore((state) => state.setNovelStatus);

export const useNovelType = () => useFilterStore((state) => state.novelType);
export const useSetNovelType = () => useFilterStore((state) => state.setNovelType);

export const useGenres = () => useFilterStore((state) => state.genres);
export const useSetGenres = () => useFilterStore((state) => state.setGenres);

export const useWorldScene = () => useFilterStore((state) => state.worldScene);
export const useSetWorldScene = () => useFilterStore((state) => state.setWorldScene);

export const useNovelAttribute = () => useFilterStore((state) => state.novelAttribute);
export const useSetNovelAttribute = () => useFilterStore((state) => state.setNovelAttribute);

export const useMainCharacterTrait = () => useFilterStore((state) => state.mainCharacterTrait);
export const useSetMainCharacterTrait = () => useFilterStore((state) => state.setMainCharacterTrait);

export const useSects = () => useFilterStore((state) => state.sects);
export const useSetSects = () => useFilterStore((state) => state.setSects);

export const useNovelVisibility = () => useFilterStore((state) => state.novelVisibility);
export const useSetNovelVisibility = () => useFilterStore((state) => state.setNovelVisibility);

export const useNovelState = () => useFilterStore((state) => state.novelState);
export const useSetNovelState = () => useFilterStore((state) => state.setNovelState);

// Ranges & Numbers
export const useTotalChapterOfNovelFrom = () => useFilterStore((state) => state.totalChapterOfNovelFrom);
export const useSetTotalChapterOfNovelFrom = () => useFilterStore((state) => state.setTotalChapterOfNovelFrom);

export const useTotalChapterOfNovelTo = () => useFilterStore((state) => state.totalChapterOfNovelTo);
export const useSetTotalChapterOfNovelTo = () => useFilterStore((state) => state.setTotalChapterOfNovelTo);

export const useRatingFrom = () => useFilterStore((state) => state.ratingFrom);
export const useSetRatingFrom = () => useFilterStore((state) => state.setRatingFrom);

export const useRatingTo = () => useFilterStore((state) => state.ratingTo);
export const useSetRatingTo = () => useFilterStore((state) => state.setRatingTo);

// Author
export const useAuthorName = () => useFilterStore((state) => state.authorName);
export const useSetAuthorName = () => useFilterStore((state) => state.setAuthorName);

export const usePage = () => useFilterStore((state) => state.page);
export const useSetPage = () => useFilterStore((state) => state.setPage);

export const useSize = () => useFilterStore((state) => state.size);
export const useSetSize = () => useFilterStore((state) => state.setSize);

// Reset
export const useResetFilters = () => useFilterStore((state) => state.resetFilters);

// Update Filter Item
export const useUpdateFilterItem = () =>
    useFilterStore((state) => state.updateFilterItem);

// Toggle Filter Item
export const useToggleFilterItem = () =>
    useFilterStore((state) => state.toggleFilterItem);