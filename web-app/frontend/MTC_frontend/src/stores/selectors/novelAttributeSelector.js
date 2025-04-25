// File: src/store/storyReaderSelectors.js
import useStoryReaderStore from './useStoryReaderStore';

// Tạo các selectors tách biệt cho từng phần của state
export const useNovelAttributeSelector = {
    // Selectors cho Sect
    useSectList: () => useStoryReaderStore((state) => state.sectList),

    useCurrentSectId: () => useStoryReaderStore((state) => state.currentSectId),

    useCurrentSect: () => {
        const currentSectId = useStoryReaderStore((state) => state.currentSectId);
        return useStoryReaderStore((state) =>
            currentSectId ? state.sectList.find(sect => sect.id === currentSectId) : null
        );
    },

    useSectById: (id) => useStoryReaderStore((state) =>
        state.sectList.find(sect => sect.id === id)
    ),

    // Selectors cho Character
    useCharacterList: () => useStoryReaderStore((state) => state.mainCharacterList),

    useCharacterById: (id) => useStoryReaderStore((state) =>
        state.mainCharacterList.find(character => character.id === id)
    ),

    // Lấy nhân vật theo một điều kiện nào đó (ví dụ: theo vai trò)
    useCharactersByRole: (role) => useStoryReaderStore((state) =>
        state.mainCharacterList.filter(character => character.role === role)
    ),

    // Selectors cho Genre
    useGenreList: () => useStoryReaderStore((state) => state.genreList),

    useGenreById: (id) => useStoryReaderStore((state) =>
        state.genreList.find(genre => genre.id === id)
    ),

    // Selectors cho World Scene
    useWorldSceneList: () => useStoryReaderStore((state) => state.worldSceneList),

    // Selectors cho trạng thái chung
    useIsLoading: () => useStoryReaderStore((state) => state.isLoading),

    useError: () => useStoryReaderStore((state) => state.error),

    // Selectors kết hợp (ví dụ: lấy tổng số danh mục)
    useStoryStats: () => {
        const sectCount = useStoryReaderStore((state) => state.sectList.length);
        const characterCount = useStoryReaderStore((state) => state.mainCharacterList.length);
        const genreCount = useStoryReaderStore((state) => state.genreList.length);
        const worldSceneCount = useStoryReaderStore((state) => state.worldSceneList?.length || 0);

        return {
            sectCount,
            characterCount,
            genreCount,
            worldSceneCount,
            totalItems: sectCount + characterCount + genreCount + worldSceneCount
        };
    },

    // Selector lọc theo tìm kiếm
    useFilteredSects: (searchTerm) => useStoryReaderStore((state) => {
        if (!searchTerm) return state.sectList;

        const normalizedSearchTerm = searchTerm.toLowerCase();
        return state.sectList.filter(sect =>
            sect.title?.toLowerCase().includes(normalizedSearchTerm) ||
            sect.content?.toLowerCase().includes(normalizedSearchTerm)
        );
    }),

    // Selector lọc nhân vật theo tên
    useFilteredCharacters: (searchTerm) => useStoryReaderStore((state) => {
        if (!searchTerm) return state.mainCharacterList;

        const normalizedSearchTerm = searchTerm.toLowerCase();
        return state.mainCharacterList.filter(character =>
            character.name?.toLowerCase().includes(normalizedSearchTerm) ||
            character.description?.toLowerCase().includes(normalizedSearchTerm)
        );
    }),

    // Selector kết hợp: Lấy cả nhân vật và chương có liên quan
    useCharacterWithRelatedSects: (characterId) => {
        const character = useStoryReaderStore(
            (state) => state.mainCharacterList.find(char => char.id === characterId)
        );

        const relatedSects = useStoryReaderStore((state) =>
            state.sectList.filter(sect =>
                sect.characterIds?.includes(characterId) ||
                sect.mainCharacterId === characterId
            )
        );

        return { character, relatedSects };
    },

    // Selector đếm số lượng chương theo trạng thái
    useSectCountByStatus: () => useStoryReaderStore((state) => {
        const counts = {
            draft: 0,
            published: 0,
            archived: 0,
            total: state.sectList.length
        };

        state.sectList.forEach(sect => {
            if (sect.status) {
                counts[sect.status] = (counts[sect.status] || 0) + 1;
            }
        });

        return counts;
    }),
};

export default useNovelAttributeSelector;