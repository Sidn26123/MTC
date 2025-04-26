import React from "react";
import LoadingSpining from './LoadingSpining.jsx';

const NovelFilter = ({onClose}) => {
    const [isLoading, setIsLoading] = React.useState(false);
    return (
        <>
            <div>
                <div>
                    <div className="relative z-10" role="dialog" aria-modal="true">
                        <div className="fixed inset-0 bg-gray-50/0 transition-opacity"></div>
                        <div className="fixed inset-0 z-10 overflow-y-auto">
                            <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                                <div className="bg-panel p-6 max-w-5/7 w-full rounded-xl background-color-lighter">
                                    {isLoading && (
                                        <LoadingSpining />
                                    )}
                                    <div className="flex justify-between items-center">
                                        <h3 className="font-bold text-xl">Mục lục</h3>
                                        <button type="button"
                                                className="rounded-md bg-panel focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2"
                                                onClick={onClose}
                                        >
                                            <span className="sr-only">Close</span>
                                            <svg className="w-6 h-6" xmlns="http://www.w3.org/2000/svg" fill="none"
                                                 viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor"
                                                 aria-hidden="true">
                                                <path strokeLinecap="round" strokeLinejoin="round"
                                                      d="M6 18 18 6M6 6l12 12"></path>
                                            </svg>
                                        </button>
                                    </div>
                                    <div
                                        className={"highlight rounded-lg grid grid-cols-1 md:grid-cols-2 gap-6"}>
                                        {/*List part*/}
                                        <div>
                                            <h2 className="text-base font-semibold text-title"
                                                data-x-text="item.name">Tình trạng</h2>
                                            <div className="leading-10 space-x-2 mt-2">
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-1" type="checkbox" value="1" /><label
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-yellow-500  peer-checked:text-white"
                                                    htmlFor="status-1"><span
                                                    data-x-text="subItem">Còn tiếp</span></label></div>
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-2" type="checkbox" value="2" /><label
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checkek:bg-yellow-500 peer-checked:text-white"
                                                    htmlFor="status-2"><span
                                                    data-x-text="subItem">Hoàn thành</span></label></div>
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-3" type="checkbox" value="3" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-3"><span
                                                    data-x-text="subItem">Tạm dừng</span></label></div>
                                            </div>
                                        </div>
                                        <div>
                                            <h2 className="text-base font-semibold text-title"
                                                data-x-text="item.name">Tình trạng</h2>
                                            <div className="leading-10 space-x-2 mt-2">
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-1" type="checkbox" value="1" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-1"><span
                                                    data-x-text="subItem">Còn tiếp</span></label></div>
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-2" type="checkbox" value="2" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-2"><span
                                                    data-x-text="subItem">Hoàn thành</span></label></div>
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-3" type="checkbox" value="3" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-3"><span
                                                    data-x-text="subItem">Tạm dừng</span></label></div>
                                            </div>
                                        </div>
                                        <div>
                                            <h2 className="text-base font-semibold text-title"
                                                data-x-text="item.name">Tình trạng</h2>
                                            <div className="leading-10 space-x-2 mt-2">
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-1" type="checkbox" value="1" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-1"><span
                                                    data-x-text="subItem">Còn tiếp</span></label></div>
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-2" type="checkbox" value="2" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-2"><span
                                                    data-x-text="subItem">Hoàn thành</span></label></div>
                                                <div className="inline-flex"><input
                                                    data-x-bind="InputSelect(item, value)"
                                                    data-x-model="selected[item.filter]" className="hidden peer"
                                                    id="status-3" type="checkbox" value="3" /><label
                                                    data-x-bind:for="item.filter+'-'+value"
                                                    className="select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out peer-checked:bg-primary peer-checked:text-white"
                                                    htmlFor="status-3"><span
                                                    data-x-text="subItem">Tạm dừng</span></label></div>
                                            </div>
                                        </div>
                                    </div>

                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export { NovelFilter };