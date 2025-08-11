const HaveReplyUserText = ({ text }) => {
    console.log(text)
    if (text === undefined || text === null || text.trim() === "") {
        return null; // Hoặc có thể trả về một giá trị mặc định khác
    }

    //in xanh phần liền kề sau @
    const regex = /@(\w+)/g;
    const parts = text.split(regex);
    return (
        <span >
            {parts.map((part, index) => {
                if (index % 2 === 1) {
                    return (
                        <span key={index} className="text-blue-500">
                            {part}
                        </span>
                    );
                }
                return part;
            })}
        </span>
    );

}

export {HaveReplyUserText}