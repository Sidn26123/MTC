import { API, API_CATEGORY } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import useFilterStore from '../stores/novelFilterStore.js';

export const getNovelProgressStatus = async () => {
    const response = await api.get(API.CATEGORY + "/novel-progress-status")
    //Add attribute value, isOnFilter to each filter item
    response.data.result = response.data.result.map((item) => ({
        ...item,
        isOnFilter: false,
        value: null,
    }));

    // useSetNovelStatus(response.data.result);
    return response;
}

const processCategoryResponse = (response) => {
    return response.data.result.map(item => ({
        ...item,
        isOnFilter: false,
        value: null,
    }));
};

export const fetchAllCategories = async () => {
    try {
        const [
            novelProgressStatus,
            novelAttributes,
            novelState,
            novelVisibility,
            genres,
            mainCharacterTraits,
            sects,
            worldScenes,
            novelTypes
        ] = await Promise.all([
            api.get(API_CATEGORY.NOVEL_PROGRESS_STATUS),
            api.get(API_CATEGORY.NOVEL_ATTRIBUTES),
            api.get(API_CATEGORY.NOVEL_STATE),
            api.get(API_CATEGORY.NOVEL_VISIBILITY),
            api.get(API_CATEGORY.GENRES),
            api.get(API_CATEGORY.MAIN_CHARACTER_TRAITS),
            api.get(API_CATEGORY.SECTS),
            api.get(API_CATEGORY.WORLD_SCENES),
            api.get(API_CATEGORY.NOVEL_TYPES),
        ]);

        return {
            novelProgressStatus: processCategoryResponse(novelProgressStatus),
            novelAttributes: processCategoryResponse(novelAttributes),
            novelState: processCategoryResponse(novelState),
            novelVisibility: processCategoryResponse(novelVisibility),
            genres: processCategoryResponse(genres),
            mainCharacterTraits: processCategoryResponse(mainCharacterTraits),
            sects: processCategoryResponse(sects),
            worldScenes: processCategoryResponse(worldScenes),
            novelTypes: processCategoryResponse(novelTypes),
        };
    } catch (error) {
        throw error;
    }
};

export const extractFiltersFromStore = () => {
    const {
        searchText,
        authorName,
        sects,
        worldScene,
        mainCharacterTrait,
        genres,
        novelProgressStatus,
        novelType,
        novelAttribute,
        totalChapterOfNovelFrom,
        totalChapterOfNovelTo,
        ratingFrom,
        ratingTo,
        sortBy,
        sortOrder,
        page,
        size,
    } = useFilterStore.getState();

    const toStringList = (arr) => {
        if (!Array.isArray(arr)) return null;

        const result = arr
            .map((item) => item?.value ?? item?.id ?? item)
            .filter((val) => val !== undefined && val !== null && val !== '');

        return result.length > 0 ? result : null;
    };
    return {
        searchText,
        authorId: authorName || undefined,
        sects: toStringList(sects.filter(item => item.isOnFilter)),
        worldScenes: toStringList(worldScene.filter(item => item.isOnFilter)),
        mainCharacterTraits: toStringList(mainCharacterTrait.filter(item => item.isOnFilter)),
        genres: toStringList(genres.filter(item => item.isOnFilter)),
        status: toStringList(novelProgressStatus.filter(item => item.isOnFilter)),
        novelType: toStringList(novelType.filter(item => item.isOnFilter)),
        novelAttribute: toStringList(novelAttribute.filter(item => item.isOnFilter)),


        // Các khoảng lọc
        chapterReadToRateFrom: totalChapterOfNovelFrom !== -1 ? totalChapterOfNovelFrom : undefined,
        chapterReadToRateTo: totalChapterOfNovelTo !== -1 ? totalChapterOfNovelTo : undefined,
        avgRateFrom: ratingFrom !== -1 ? ratingFrom : undefined,
        avgRateTo: ratingTo !== -1 ? ratingTo : undefined,

        // Sắp xếp
        sortBy,
        sortDirection: sortOrder?.toUpperCase() === 'DESC' ? 'DESC' : 'ASC',

        // Phân trang mặc định
        page: page,
        size: size,
    };
};

