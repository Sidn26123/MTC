import React, { useEffect, useRef } from 'react';

const CanvasLine = ({ text, fontSize = 20, fontFamily = "Palatino", watermark = "MyWeb" }) => {
    const canvasRef = useRef(null);

    useEffect(() => {
        const canvas = canvasRef.current;
        const ctx = canvas.getContext("2d");

        // Font trước khi đo
        ctx.font = `${fontSize}px ${fontFamily}`;
        const padding = 4;
        const textWidth = ctx.measureText(text).width;
        const lineHeight = fontSize * 1.5;

        // Kích thước canvas
        canvas.width = textWidth + padding * 2;
        canvas.height = lineHeight;

        // Vẽ chữ chính
        ctx.fillStyle = "#000";
        ctx.font = `${fontSize}px ${fontFamily}`;
        ctx.textBaseline = "top";
        ctx.fillText(text, padding, 0);

        // Vẽ watermark mờ chéo
        ctx.save();
        ctx.globalAlpha = 0.1; // độ mờ
        ctx.translate(canvas.width / 2, canvas.height / 2);
        ctx.rotate(-Math.PI / 6); // xoay 30 độ
        ctx.font = `bold ${fontSize * 0.8}px sans-serif`;
        ctx.fillStyle = "#fff"; // màu watermark
        ctx.textAlign = "center";
        ctx.textBaseline = "middle";
        ctx.fillText(watermark, 0, 0);
        ctx.restore();
    }, [text, fontSize, fontFamily, watermark]);

    return (
        <canvas
            ref={canvasRef}
            style={{
                display: "inline-block",
                verticalAlign: "middle",
            }}
        />
    );
};

const Content = ({ content }) => {
    const lines = content.replace(/\\n/g, '\n').split('\n').filter(line => line.trim() !== '');
    console.log("Content lines:", lines);


    const renderLine = (line, index) => {
        // Kiểm tra nếu dòng chứa markdown image syntax ![alt](url)
        // Hỗ trợ: ![alt text](https://example.com/image.jpg?query=abc)
        const imageRegex = /!\[([^\]]*?)\]\((https?:\/\/[^\s)]+)\)/g;
        const imageMatches = [...line.matchAll(imageRegex)];
        console.log("Image matches in line:", imageMatches);
        if (imageMatches.length > 0) {
            // Nếu có ảnh trong dòng
            let lastIndex = 0;
            const elements = [];

            imageMatches.forEach((match, matchIndex) => {
                const [fullMatch, altText, imageUrl] = match;
                const matchStart = match.index;

                // Thêm text trước ảnh (nếu có)
                if (matchStart > lastIndex) {
                    const textBefore = line.substring(lastIndex, matchStart);
                    if (textBefore.trim()) {
                        elements.push(
                            <span key={`text-${index}-${matchIndex}`}>{textBefore}</span>
                        );
                    }
                }

                // Thêm ảnh
                elements.push(
                    <div key={`img-${index}-${matchIndex}`} className="my-4">
                        <img
                            src={imageUrl}
                            alt={altText || "Ảnh trong chương"}
                            className="max-w-full h-auto rounded-lg shadow-md mx-auto block"
                            loading="lazy"
                            onError={(e) => {
                                // Xử lý lỗi load ảnh
                                e.target.style.display = 'none';
                                const errorDiv = document.createElement('div');
                                errorDiv.className = 'text-red-500 text-sm text-center p-2 border border-red-200 rounded bg-red-50';
                                errorDiv.textContent = 'Không thể tải ảnh';
                                e.target.parentNode.appendChild(errorDiv);
                            }}
                        />
                    </div>
                );

                lastIndex = matchStart + fullMatch.length;
            });

            // Thêm text sau ảnh cuối cùng (nếu có)
            if (lastIndex < line.length) {
                const textAfter = line.substring(lastIndex);
                if (textAfter.trim()) {
                    elements.push(
                        <span key={`text-end-${index}`}>{textAfter}</span>
                    );
                }
            }

            return (
                <div key={index} className="mb-4 text-lg font-palatino text-[20px] leading-relaxed">
                    {elements}
                </div>
            );
        } else {
            // Dòng text thông thường
            const useCanvas = index > 5 && Math.random() < 0;

            return (
                <div
                    key={index}
                    className="mb-4 text-lg font-palatino text-[20px] leading-relaxed"
                >
                    {useCanvas ? <CanvasLine text={line} /> : line}
                </div>
            );
        }
    };

    return (
        <div className="chapter-content">
            {lines.map((line, index) => renderLine(line, index))}
        </div>
    );
}

export default Content;