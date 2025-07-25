import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router';
import { deleteBookshelfItem, getBookshelfItems } from '../../services/bookshelfService.js';
import { timeAgo } from '../../utils/DatetimeUtil.js';
import { faX } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function JustReadNovel() {
    const navigate = useNavigate();
    const [data, setData] = React.useState([]);
    const page = {
        page:0,
        size:5,
        sort: "updatedAt",
        direction: "desc"
    }

    useEffect(() => {
        getBookshelfItems("693fdba8-5657-4625-8fd9-1c9f7bbbb5d5", page).then((response) => {
            if (response.data.result) {
                setData(response.data.result);
                console.log("Fetched novels:", response.data.result);

            } else {
                console.error("Failed to fetch bookshelf items.");
            }
        })
    }, []);

    function handleDeleteItem(id) {
        deleteBookshelfItem(id, data.novel.id).then((response) => {
            if (response.data.result) {
                console.log("Deleted bookshelf item successfully");
                // Optionally, you can refresh the bookshelf items
            } else {
                console.error("Failed to delete bookshelf item");
            }
        });

    }
    function handleGotoNovel(novelSlug) {
        navigate(`/truyen/${novelSlug}`, {
            state: {
                from: 'JustReadNovel'
            }
        });

    }

    function handleGotoNovelWithChapter(slug, currentChapterIdx) {
        navigate(`/truyen/${slug}/chuong-${currentChapterIdx}`, {
            state: {
                from: 'JustReadNovel'
            }
        })
    }

    return (
        <>
            <div className={"flex flex-col gap-y-5"}>
                <div className={"flex flex-row justify-between items-center pt-5"}>
                    <span>TRUYỆN VỪA ĐỌC</span>
                    <Link to={"/#"}>--</Link>
                </div>
                {/*Data table*/}
                <div className="relative overflow-x-auto shadow-md sm:rounded-lg select-none">
                    <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                        <tbody>
                        {data.data && data.data.map((item, index) => (
                            <tr key={index } className="border-b dark:border-gray-700 border-gray-200">
                                <th scope="row"
                                    className="px-6 py-4 w-1/8 font-medium whitespace-nowrap base-text-color">
                                    {timeAgo(item.updatedAt)}
                                </th>
                                <td onClick={() => handleGotoNovel(item.novel.slug)}  className="px-6 py-4 w-4/8 hover:text-yellow-500 hover:cursor-pointer">
                                    {item.novel.name}
                                </td>
                                <td onClick={() => handleGotoNovelWithChapter(item.novel.slug, item.currentChapterIdx)} className="px-6 py-4 w-1/8 hover:cursor-pointer">
                                    Đã đọc {item.currentChapterIdx}/{item.novel.totalChapters}
                                </td>

                                <td className="px-6 py-4 text-right w-1/8">
                                    <FontAwesomeIcon
                                        icon={faX}
                                        onClick={() => handleDeleteItem(data.id, data.novel.id)}
                                        className={'mr-3 hover:cursor-pointer'}
                                    />
                                </td>
                            </tr>
                        ))}

                        </tbody>
                    </table>
                </div>
            </div>
        </>
    );
}

export default JustReadNovel;