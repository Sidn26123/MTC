import React from "react";
import { Link } from 'react-router';
import { getDestinationWhenClickMinialCard } from '../../services/navigateService.js';

function MinialIntroduceNovelCard({data}) {
    return (
        <>
            <Link to={getDestinationWhenClickMinialCard(data)} className={"flex flex-col items-center"}>
                <div className={"flex flex-col items-center"}>
                    <img src={data.novelCoverImage} alt={"Alt"} />
                    <span>{data.name}</span>
                </div>
            </Link>
        </>
    );
}

export default MinialIntroduceNovelCard;