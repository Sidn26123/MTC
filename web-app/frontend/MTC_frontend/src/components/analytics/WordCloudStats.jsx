import React from 'react';
import WordCloud from 'react-d3-cloud';
import ChartContainer from './ChartContainer.jsx';

const WordCloudStats = ({ title, data }) => {
    const words = data.map(d => ({ text: d.word, value: d.count }));

    const fontSizeMapper = word => Math.log2(word.value) * 10; // scale kích thước chữ
    const rotate = () => ~~(Math.random() * 2) * 90; // random xoay 0° hoặc 90°

    return (
        <ChartContainer>
            <h3 className="font-semibold mb-3">{title}</h3>
            <div style={{ height: 300 }}>
                <WordCloud
                    data={words}
                    fontSizeMapper={fontSizeMapper}
                    rotate={rotate}
                    padding={2}
                    width={500}  // bạn có thể set dynamic theo container
                    height={300}
                />
            </div>
        </ChartContainer>
    );
};

export default WordCloudStats;
