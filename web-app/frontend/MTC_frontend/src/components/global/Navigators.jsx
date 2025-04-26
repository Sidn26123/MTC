import React from "react";
import { SimpleDropdown } from '../../common/Components.jsx';
import { faBackwardStep, faChevronLeft, faChevronRight, faForwardStep } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

const DefaultNavigator = () => {
    return (
        <>
            <div>
                <div className={"flex flex-row justify-end items-center gap-x-1 w-full"}>
                    <div></div>
                    <div className={"flex flex-row justify-between gap-x-2 items-center"}>
                        <span>Số dòng trên trang</span>
                        <div className={"w-36"}>
                            <SimpleDropdown />

                        </div>
                        <span>1-2 cua 2</span>
                        <div className={"gap-x-2 flex flex-row"}>
                            <FontAwesomeIcon icon={faBackwardStep} />
                            <FontAwesomeIcon icon={faChevronLeft} />
                            <FontAwesomeIcon icon={faChevronRight} />
                            <FontAwesomeIcon icon={faForwardStep} />
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export { DefaultNavigator };