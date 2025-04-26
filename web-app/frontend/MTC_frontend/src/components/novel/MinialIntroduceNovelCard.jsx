import React from "react";

function MinialIntroduceNovelCard({data}) {
    return (
        <>
            <div className={"flex flex-col items-center"}>
                <img src={data.image.src} alt={data.image.alt} />
                <span>{data.name}</span>
            </div>
        </>
    );
}

export default MinialIntroduceNovelCard;