import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import { Link, useNavigate, useParams } from 'react-router';
import { getChapterById, getCurrentChapterContent, updateChapter } from '../../services/chapterService.js';
import { useCurrentChosenChapter, useCurrentPublishedNovel } from '../../stores/publisherStore.js';
import { showSuccess } from '../../utils/ToastUtils.js';

const ChapterEditPage = () => {
    const navigate = useNavigate();
    const [data, setData] = useState({});
    const {chapterId } = useParams();
    const chapter = useCurrentChosenChapter();
    const currentNovel = useCurrentPublishedNovel();
    useEffect(() => {
        getChapterById(chapterId)
            .then(response => {
                if (response.status === 200) {
                    var data = response.data.result;
                    var slug = currentNovel.slug;

                    setData(data);
                    getCurrentChapterContent({novelSlug: slug, chapterIdx: data.chapterIdx}).then(r => {
                        if (r.data.code === 0){
                            setData(prevData => ({
                                ...prevData,
                                content: r.data.result.content
                            }));
                        }

                    })
                } else {
                    console.error("Failed to fetch chapter data");
                }
            })
            .catch(error => {
                console.error("Error fetching chapter data:", error);
            });
    }, []);


    function handleUpdateChapter() {
        console.log("Updating chapter with data:", data);
        updateChapter(chapterId, data).then( response => {
            if (response.status === 200){
                showSuccess("Cập nhật chương thành công");
                navigate(`/bookhub/novels/${currentNovel.slug}/chapters`);
            }
        });
    }

    return (
        <>
            <div>
                <div>
                    <div className={'flex flex-col'}>
                        <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                            <h1>{currentNovel.name}</h1>
                            <div className={"flex flex-col gap-y-2 mt-5"}>
                                <span>Tên truyện</span>
                                <input
                                    value={data.name || ''}
                                    onChange={(e) => setData({ ...data, chapterIdx: e.target.value })}
                                    className={'w-full border border-gray-500 rounded-md p-1 py-3  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                />
                            </div>
                            <div className={"mt-5"}>
                                <textarea
                                    value={data.content || ''}
                                    onChange={(e) => setData({ ...data, content: e.target.value })}
                                    className={'w-full border border-gray-500 rounded-md p-1 py-3 min-h-48 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                />
                            </div>

                            <div className={"flex flex-col gap-y-2 mt-5"}>
                                <span className={"text-sm text-gray-500"}>STT</span>
                                <input
                                    className={'w-full border border-gray-500 rounded-md p-1 py-3  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                />
                            </div>
                            <div>
                                <div className={"flex flex-row justify-center items-center gap-x-5 w-full"}>
                                    <div className={"w-1/2"}>
                                        <button
                                            className={"bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}

                                        >
                                            <div>
                                                <div className={"flex justify-center items-center gap-x-2"}>
                                                    <span className={"ml-2"}>Nhập Lại</span>
                                                    <FontAwesomeIcon icon={faDeleteLeft} />
                                                </div>
                                            </div>
                                        </button>
                                    </div>
                                    <div className={'w-1/2'} onClick = {handleUpdateChapter}>
                                        <button
                                            className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}

                                        >
                                            <div>
                                                <div className={"flex justify-center items-center gap-x-2"}>
                                                    <span className={"ml-2"}>Cập nhật</span>
                                                    <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                                </div>
                                            </div>

                                        </button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ChapterEditPage;