import React, { useEffect, useState } from 'react';
import { CategoryDropdown, CustomDatePicker, SimpleDropdown } from '../../common/CommonComponents.jsx';
import DateTimePicker from 'react-datetime-picker';
import 'react-datetime-picker/dist/DateTimePicker.css';
import 'react-calendar/dist/Calendar.css';
import 'react-clock/dist/Clock.css';
import { formatPublishDateTime } from '../../utils/DatetimeUtil.js';
import { faArrowUpFromBracket, faDeleteLeft } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link, redirect } from 'react-router';
import {
    useGenres, useMainCharacterTrait,
    useNovelAttribute,
    useNovelProgressStatus,
    useNovelState, useNovelType, useNovelVisibility, useSects, useSetNovelStatus, useSetPage, useWorldScene,
} from '../../stores/selectors/novelFilterSelector.js';
import { getObjectFromList } from '../../utils/Utils.js';
import { useCurrentNovelPublisher } from '../../stores/userStores.js';
import { usePublishedByPublisher } from '../../stores/novelStore.js';
import {
    useCurrentChosenPublishedNovel,
    useCurrentPublishedNovel,
    usePublisherStore,
} from '../../stores/publisherStore.js';


const AddChapterPage = () => {

    const novelProgressStatus = useNovelProgressStatus();
    const novelAttributes = useNovelAttribute();
    const novelState = useNovelState();
    const novelVisibility = useNovelVisibility();
    const genres = useGenres();
    const mainCharacterTraits = useMainCharacterTrait();
    const sects = useSects();
    const worldScenes = useWorldScene();
    const novelTypes = useNovelType();
    const changePage = useSetPage();
    const setNovelProgressStatus = useSetNovelStatus();


    const currentPublishedNovel = useCurrentPublishedNovel();
    console.log("Current Published Novel:", currentPublishedNovel);
    // const currentNovel = useNovelState();
    const [showDateTimePicker, setShowDateTimePicker] = React.useState(false);
    const [value, setValue] = useState(new Date());
    const [formattedDate, setFormattedDate] = useState("");
    const [mode, setMode] = useState("writing"); // writing, confirm
    const getFormattedDate = (date) => {
        return formatPublishDateTime(date, "dd/MM/yyyy HH:mm");
    };
    const typeAdd = [{id: "normal", name: "Đăng thường"}, {id: "insert", name: "Chèn chương"}];
    const [currentType, setCurrentType] = useState(typeAdd[0]);

    const publishChapter = () => {
        redirect("/bookhub/published-novel");
    }

    // Cập nhật giá trị khi component render
    useEffect(() => {
        setFormattedDate(getFormattedDate(value));
    }, [value]);


    function handleSelectField(selected) {
        console.log("Selected Type ID:", selected);
        setCurrentType(getObjectFromList(typeAdd, "id", selected.id));
    }

    return (
        <div>
            {mode === "writing" ? (
                <div className={"flex flex-col bg-background-light rounded-md p-5"}>
                    <div className={""}>
                        <h1>Thêm chương</h1>
                        <span>Truyện đầu tiên</span>
                    </div>
                    <div className={"flex flex-row justify-between items-center mt-10"}>
                        <div className={"flex flex-col gap-y-2 w-1/2 p-2 pl-3"}>
                            <span className={'text-sm'}>Loại</span>
                            <CategoryDropdown
                                dropdown={typeAdd}
                                placeholder={'Chọn thể loại'}
                                onSelect={(selected) => handleSelectField( selected)}
                                defaultValue={currentType}
                            />

                        </div>
                        <div className={'flex flex-col gap-y-1 w-1/2'}>
                            <span>STT</span>
                            <input
                                className={'w-full border border-gray-500 rounded-md p-1  hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                disabled={currentType.id === "normal"}
                                value={currentPublishedNovel.totalChapters + 1}
                            />
                        </div>
                    </div>
                    <div className={'mt-10 p-3'}>
                        <span>Nội dung các chương</span>
                        <textarea
                            className={"w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0"} />
                    </div>
                    <div className={'flex flex-row justify-between items-center gap-x-5 mt-10'}>
                        <div className={'flex flex-col gap-y-2 w-1/2 ml-2 relative'}>
                            <span>Hen gio</span>
                            <input
                                className={'w-full select-none border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 hover:cursor-pointer focus:border-gray-400 focus:outline-none focus:ring-0'}
                                readOnly
                                value={formattedDate}
                                onClick={() => setShowDateTimePicker(!showDateTimePicker)}
                            />
                            {showDateTimePicker && (
                                <div
                                    className={'absolute text-gray-500'}>
                                    <div className={''}>
                                        <CustomDatePicker value={value} onChange={setValue} />
                                    </div>

                                </div>
                            )}

                        </div>
                        <div className={'flex flex-col gap-y-2 w-1/2'}>
                            <span>Thu phi</span>
                            <input
                                className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                            />

                        </div>

                    </div>
                    <div className={'flex flex-row justify-center items-center gap-x-5 mt-10'}>
                        <button
                            className={"bg-yellow-primary w-48 text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}
                            onClick={() => setMode("confirm")}
                        >
                            Thêm
                        </button>
                    </div>
                </div>

            ) :
            (
                <ConfirmAddNovel onBack={() => setMode("writing")} onNext={() => publishChapter()}/>
            )}
        </div>
    )
}

const NormalAddChapterMode = () => {

}


const ConfirmAddNovel = ({onBack, onNext}) => {
    return (
        <>
            <div>
                <div className={"flex flex-col"}>
                    <div className={"flex flex-col bg-background-light rounded-md p-5 mt-2"}>
                        <span className={"text-md"}>
                            Truyen Dau Tien
                        </span>
                            <span>
                            Đã tách 1 chương
                        </span>
                    </div>
                    <div className={"bg-background-light rounded-md mt-5 p-5"}>
                        <div className={'flex flex-col'}>
                            <span className={"text-sm"}>Thu phí</span>
                            <input
                                className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                                readOnly={true}
                            />

                        </div>
                        <div className={'flex flex-col mt-8'}>
                            <span className={"text-sm"}>Tên chương</span>
                            <input
                                className={'w-full border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'}
                            />

                        </div>
                        <div className={'flex flex-col mt-8'}>
                            <span className={"text-sm"}>Nội dung</span>
                            <textarea
                                className={'w-full min-h-48 border border-gray-500 rounded-md p-2 mt-2 hover:border-gray-400 focus:border-gray-400 focus:outline-none focus:ring-0'} />
                            <span className={'text-xs text-gray-500 mt-3'}>Số từ: 0</span>
                        </div>
                    </div>
                </div>
                <div className={"flex flex-row justify-center items-center gap-x-5 w-full"}>
                    <div className={"w-1/2"}>
                        <button
                            className={"bg-cus-gray w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer"}
                            onClick={onBack}

                        >
                            <div className={"flex justify-center items-center gap-x-2"}>
                                <span className={"ml-2"}>Nhập Lại</span>
                                <FontAwesomeIcon icon={faDeleteLeft} />
                            </div>
                        </button>
                    </div>
                    <div className={'w-1/2'}>
                        <button
                            className={'bg-yellow-primary w-full text-white rounded-md p-2 mt-10 hover:bg-yellow-500 focus:outline-none focus:ring-0 hover:cursor-pointer'}
                            onClick={onNext}

                        >
                            <Link to={"/bookhub/published"}>
                                <div className={"flex justify-center items-center gap-x-2"}>
                                    <span className={"ml-2"}>Đăng Chương</span>
                                    <FontAwesomeIcon icon={faArrowUpFromBracket} />
                                </div>
                            </Link>

                        </button>
                    </div>
                </div>
            </div>
        </>
    )
}

export default AddChapterPage;

