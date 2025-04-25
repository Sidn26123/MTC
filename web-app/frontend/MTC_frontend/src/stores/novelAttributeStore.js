import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';
import {API} from '../configurations/configuration';
// Tạo store với middleware
const useStoryReaderStore = create(
    devtools(
        persist(
            (set, get) => ({
                // Initial state
                // sectList: [],
                sectList: [],
                mainCharacterList: [],
                genreList: [],
                worldSceneList: [],
                isLoading: false,
                error: null,

                // Sect actions
                fetchSects: async () => {
                    set({ isLoading: true, error: null });
                    try {
                        // Thay thế bằng API call thực tế
                        const response = await fetch(API.NOVEL + "/sects");
                        const data = await response.json();
                        set({ sectList: data, isLoading: false });
                    } catch (error) {
                        set({
                            error: error.message || 'Lỗi khi tải danh sách chương',
                            isLoading: false
                        });
                    }
                },

                addSect: (sect) => {
                    set((state) => ({
                        sectList: [...state.sectList, sect]
                    }));
                },

                updateSect: (id, sectData) => {
                    set((state) => ({
                        sectList: state.sectList.map((sect) =>
                            sect.id === id ? { ...sect, ...sectData, updatedAt: new Date().toISOString() } : sect
                        )
                    }));
                },

                deleteSect: (id) => {
                    set((state) => ({
                        sectList: state.sectList.filter((sect) => sect.id !== id)
                    }));
                },

                setCurrentSect: (id) => {
                    set({ currentSectId: id });
                },

                // Character actions
                fetchCharacters: async () => {
                    set({ isLoading: true, error: null });
                    try {
                        // Thay thế bằng API call thực tế
                        const response = await fetch('/api/characters');
                        const data = await response.json();
                        set({ mainCharacterList: data, isLoading: false });
                    } catch (error) {
                        set({
                            error: error.message || 'Lỗi khi tải danh sách nhân vật',
                            isLoading: false
                        });
                    }
                },

                addCharacter: (character) => {
                    set((state) => ({
                        mainCharacterList: [...state.mainCharacterList, character]
                    }));
                },

                updateCharacter: (id, characterData) => {
                    set((state) => ({
                        mainCharacterList: state.mainCharacterList.map((character) =>
                            character.id === id ? { ...character, ...characterData } : character
                        )
                    }));
                },

                deleteCharacter: (id) => {
                    set((state) => ({
                        mainCharacterList: state.mainCharacterList.filter((character) => character.id !== id)
                    }));
                },

                // Genre actions
                fetchGenres: async () => {
                    set({ isLoading: true, error: null });
                    try {
                        // Thay thế bằng API call thực tế
                        const response = await fetch('/api/genres');
                        const data = await response.json();
                        set({ genreList: data, isLoading: false });
                    } catch (error) {
                        set({
                            error: error.message || 'Lỗi khi tải danh sách thể loại',
                            isLoading: false
                        });
                    }
                },

                addGenre: (genre) => {
                    set((state) => ({
                        genreList: [...state.genreList, genre]
                    }));
                },

                updateGenre: (id, genreData) => {
                    set((state) => ({
                        genreList: state.genreList.map((genre) =>
                            genre.id === id ? { ...genre, ...genreData } : genre
                        )
                    }));
                },

                deleteGenre: (id) => {
                    set((state) => ({
                        genreList: state.genreList.filter((genre) => genre.id !== id)
                    }));
                },

                // Các action trạng thái chung
                setLoading: (status) => {
                    set({ isLoading: status });
                },

                setError: (error) => {
                    set({ error });
                },

                resetStore: () => {
                    set({
                        sectList: [],
                        mainCharacterList: [],
                        genreList: [],
                        currentSectId: null,
                        isLoading: false,
                        error: null
                    });
                },


            }),
            {
                name: 'novel-attribute-storage',
                // Chỉ lưu trữ các state quan trọng vào localStorage
                partialize: (state) => ({
                    currentSectId: state.currentSectId,
                    // Có thể thêm các state khác cần lưu trữ
                }),
            }
        )
    )
);



export default useStoryReaderStore;