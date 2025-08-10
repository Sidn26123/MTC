import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import { Link, useParams } from 'react-router';
import { getChapterById, getCurrentChapterContent, updateChapter } from '../../services/chapterService.js';
import { useCurrentChosenChapter } from '../../stores/publisherStore.js';

const ChapterEditPage = () => {
    const [data, setData] = useState({});
    const {chapterId } = useParams();
    const chapter = useCurrentChosenChapter();
    useEffect(() => {
        getChapterById(chapterId)
            .then(response => {
                if (response.status === 200) {
                    var data = response.data.result;
                    setData(data);
                    getCurrentChapterContent({slug: data.slug, chapterIdx: data.chapterIdx}).then(r => {
                        if (r.status === 200) {
                            setData(prevData => ({
                                ...prevData,
                                content: r.data.result.content
                            }));
                        } else {
                            console.error("Failed to fetch chapter content");
                        }
                    })
                    console.log("Chapter data fetched successfully:", response.data.result);
                } else {
                    console.error("Failed to fetch chapter data");
                }
            })
            .catch(error => {
                console.error("Error fetching chapter data:", error);
            });
    }, []);


    function handleUpdateChapter() {
        updateChapter(chapterId, data).then( response => {});
    }

    return (
        <>
            <div>
                <div>
                    <div className={'flex flex-col'}>
                        <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                            <h2>Cửu Tinh Bá Thể Quyết</h2>
                            <span>{data.name}</span>
                            <div className={"flex flex-col gap-y-2 mt-5"}>
                                <span>STT</span>
                                <input
                                    value={data.chapterIdx || ''}
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
                                            <Link to={"/bookhub/books/1/chapters"}>
                                                <div className={"flex justify-center items-center gap-x-2"}>
                                                    <span className={"ml-2"}>Nhập Lại</span>
                                                    <FontAwesomeIcon icon={faDeleteLeft} />
                                                </div>
                                            </Link>
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