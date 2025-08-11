import { API, API_CATEGORY } from '../configurations/configuration';
import api from '../middlewares/axios.js';
import { useSetListNovel } from '../stores/novelStore.js';
import { wrapApiFunctions } from '../components/wrapper/apiWrapper.js';
import { getRatingOfNovel, sendComment, sendRating } from './feedbackService.js';

export const getNovels = async () => {
    const response = await api.get(API.NOVEL +"/novels")

    // useSetNovelStatus(response.data.result);
    // useSetListNovel(response.data);
    return response;
}

export const getNovelBySlug = async (slug) => {
    const response = await api.get(API.NOVEL + "/novels/" + slug);
    return response;
}
export const getNovelById = async (id) => {
    const response = await api.get(API.NOVEL + "/novels/id/" + id);
    return response;
}
export const getFilteredNovels = async (filter) => {
    console.log("getFilteredNovels", filter);
    const response = await api.post(API.NOVEL + "/novels/filter", filter);
    return response;    
};


export const isNovelBookmarked = async (novelId, novelList) => {
    //check if novelId is in novelList
    const isBookmarked = novelList.some((novel) => novel.id === novelId);
    return isBookmarked;
}

export const getAllMyNovels = async () => {
    const response = await api.get(API.NOVEL + "/novels/my/novels/all");
    return response;
}

// export const getJustReadNovel = async

export const getTopPromotionNovels = async () => {
    const response = await api.get(API.NOVEL + "/novel-statistic/top/promotions",
        {
            params: {
                page: 1,
                limit: 10
            }
        });
    return response;
}

export const getBestNovels = async () => {
    const response = await api.get(API.NOVEL + "/novels/top/best",
        {
            params: {
                limit: 10
            }
        });
    return response;
}



// genres

export const getAllGenres = async () => {
    const response = await api.get(API_CATEGORY.GENRES);
    return response.data;
}

export const addGenre = async (genre) => {
    const response = await api.post(API_CATEGORY.GENRES + "/create", genre);
    return response.data;
}

export const updateGenre = async (genre) => {
    const response = await api.put(`${API_CATEGORY.GENRES}/${genre.id}`, {
        name: genre.name,
    });
    return response.data;
};

// sect

export const getAllSect = async () => {
    const response = await api.get(API_CATEGORY.SECTS);
    return response.data;
}

export const addSect = async (sect) => {
    const response = await api.post(API_CATEGORY.SECTS + "/create", sect);
    return response.data;
}

export const updateSect = async (sect) => {
    const response = await api.put(`${API_CATEGORY.SECTS}/${sect.id}`, {
        name: sect.name,
    });
    return response.data;
};


// world scene

export const getAllWorldScene = async () => {
    const response = await api.get(API_CATEGORY.WORLD_SCENES);
    return response.data;
}

export const addWorldScene = async (WorldScene) => {
    const response = await api.post(API_CATEGORY.WORLD_SCENES + "/create", WorldScene);
    return response.data;
}

export const updateWorldScene = async (WorldScene) => {
    const response = await api.put(`${API_CATEGORY.WORLD_SCENES}/${WorldScene.id}`, {
        name: WorldScene.name,
    });
    return response.data;
};


// main character traits
export const getAllMainCharacterTraits = async () => {
    const response = await api.get(API_CATEGORY.MAIN_CHARACTER_TRAITS);
    return response.data;
}

export const addMainCharacterTrait = async (trait) => {
    const response = await api.post(API_CATEGORY.MAIN_CHARACTER_TRAITS + "/create", trait);
    return response.data;
}

export const updateMainCharacterTrait = async (trait) => {
    const response = await api.put(`${API_CATEGORY.MAIN_CHARACTER_TRAITS}/${trait.id}`, {
        name: trait.name,
    });
    return response.data;
}

// novel types
export const getAllNovelState = async () => {
    const response = await api.get(API_CATEGORY.NOVEL_STATE);
    return response.data;
}

export const updateNovelState = async (novelId, newStatusId) => {
    const response = await api.put(`${API.NOVEL}/novels/${novelId}`, {
        novelState: newStatusId,
    });
    return response.data;
}


const apiFunctions = {
    // novels
    getNovels,
    getNovelBySlug,
    getNovelById,
    getFilteredNovels,
    isNovelBookmarked,
    getAllMyNovels,
    getTopPromotionNovels,
    getBestNovels,

    // genres
    getAllGenres,
    addGenre,
    updateGenre,

    // sect
    getAllSect,
    addSect,
    updateSect,

    // world scene
    getAllWorldScene,
    addWorldScene,
    updateWorldScene,

    // main character traits
    getAllMainCharacterTraits,
    addMainCharacterTrait,
    updateMainCharacterTrait,

    // novel state
    getAllNovelState,
    updateNovelState
};

export default wrapApiFunctions(apiFunctions);
