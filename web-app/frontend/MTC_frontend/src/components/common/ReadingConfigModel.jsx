import React, { useState } from 'react';
import clsx from 'clsx';

const presetColors = [
    '#F8FAFC', '#f4f4f4', '#e9ebee', '#d5d8dc', '#f4f4e4', '#f5ebcd', '#eae4d3', '#f2f2f2', '#c2b49b', '#272729', '#232323', '#1e293b'
];

const fontFamilies = [
    'Avenir Next', 'Bookerly', 'Segoe UI', 'Literata', 'Baskerville', 'Arial', 'Courier New', 'Tahoma', 'Palatino Linotype', 'Georgia', 'Verdana', 'Times New Roman', 'Source Sans Pro'
];

const fontSizes = Array.from({ length: 39 }, (_, i) => `${12 + i}px`);
const lineHeights = Array.from({ length: 21 }, (_, i) => `${100 + i * 10}%`);
const textAlignOptions = ['left', 'justify', 'center', 'right'];

export default function ReaderConfigModal({ isOpen, onClose }) {
    const [theme, setTheme] = useState('light');
    const [config, setConfig] = useState({
        'background-color': {
            light: '#f4f4f4',
            dark: '#1e293b',
        },
        color: {
            light: '#000000',
            dark: '#ffffff',
        },
        'font-family': 'Avenir Next',
        'font-size': '24px',
        'line-height': '150%',
        'text-align': 'left',
    });

    const onChangeConfig = (key, value, subkey) => {
        if (subkey) {
            setConfig((prev) => ({
                ...prev,
                [key]: {
                    ...prev[key],
                    [subkey]: value,
                },
            }));
        } else {
            setConfig((prev) => ({
                ...prev,
                [key]: value,
            }));
        }
    };

    const resetConfig = () => {
        setConfig({
            'background-color': {
                light: '#f4f4f4',
                dark: '#1e293b',
            },
            color: {
                light: '#000000',
                dark: '#ffffff',
            },
            'font-family': 'Avenir Next',
            'font-size': '24px',
            'line-height': '150%',
            'text-align': 'left',
        });
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-40">
            <div className="bg-panel border border-primary p-6 max-w-md w-full rounded-xl relative z-10">
                <div className="flex justify-between items-center">
                    <img className="h-8 w-auto" src="https://assets.metruyencv.com/build/assets/logo-776b73c9.png" alt="logo" />
                    <h3 className="font-bold text-xl">Cài đặt đọc truyện</h3>
                    <button onClick={onClose} className="rounded-md bg-panel focus:outline-none focus:ring-2 focus:ring-primary">
                        <span className="sr-only">Close</span>
                        <svg className="w-6 h-6" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor">
                            <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                        </svg>
                    </button>
                </div>

                <div className="space-y-6 mt-10">
                    <div className="flex gap-4 mb-4">
                        <button onClick={() => setTheme('light')} className={clsx("px-3 py-1 rounded", theme === 'light' && 'bg-primary text-white')}>Sáng</button>
                        <button onClick={() => setTheme('dark')} className={clsx("px-3 py-1 rounded", theme === 'dark' && 'bg-primary text-white')}>Tối</button>
                    </div>

                    {['light', 'dark'].map((mode) => (
                        <div key={mode} className={clsx(theme !== mode && 'hidden', 'space-y-4')}>
                            <div className="flex items-center space-x-3">
                                <label className="w-64 text-sm font-medium">Màu nền [{mode === 'light' ? 'ngày' : 'đêm'}]</label>
                                <input type="color" value={config['background-color'][mode]} onChange={(e) => onChangeConfig('background-color', e.target.value, mode)} className="w-32" list={`presetColors-${mode}`} />
                                <datalist id={`presetColors-${mode}`}>
                                    {presetColors.map((c) => <option key={c} value={c} />)}
                                </datalist>
                            </div>
                            <div className="flex items-center space-x-3">
                                <label className="w-64 text-sm font-medium">Màu chữ [{mode === 'light' ? 'ngày' : 'đêm'}]</label>
                                <input type="color" value={config.color[mode]} onChange={(e) => onChangeConfig('color', e.target.value, mode)} className="w-32" />
                            </div>
                        </div>
                    ))}

                    <div className="flex items-center space-x-3">
                        <label className="w-64 text-sm font-medium">Font chữ</label>
                        <select value={config['font-family']} onChange={(e) => onChangeConfig('font-family', e.target.value)} className="w-full text-black rounded-md">
                            {fontFamilies.map((f) => <option key={f} value={f}>{f}</option>)}
                        </select>
                    </div>

                    <div className="flex items-center space-x-3">
                        <label className="w-64 text-sm font-medium">Cỡ chữ</label>
                        <select value={config['font-size']} onChange={(e) => onChangeConfig('font-size', e.target.value)} className="w-full text-black rounded-md">
                            {fontSizes.map((s) => <option key={s} value={s}>{s}</option>)}
                        </select>
                    </div>

                    <div className="flex items-center space-x-3">
                        <label className="w-64 text-sm font-medium">Chiều cao dòng</label>
                        <select value={config['line-height']} onChange={(e) => onChangeConfig('line-height', e.target.value)} className="w-full text-black rounded-md">
                            {lineHeights.map((lh) => <option key={lh} value={lh}>{lh}</option>)}
                        </select>
                    </div>

                    <div className="flex items-center space-x-3">
                        <label className="w-64 text-sm font-medium">Canh chữ</label>
                        <select value={config['text-align']} onChange={(e) => onChangeConfig('text-align', e.target.value)} className="w-full text-black rounded-md">
                            {textAlignOptions.map((align) => <option key={align} value={align}>{`Canh ${align === 'justify' ? 'đều' : align}`}</option>)}
                        </select>
                    </div>

                    <div className="pt-4 flex justify-center">
                        <button onClick={resetConfig} className="px-4 py-1 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50">Mặc định</button>
                    </div>
                </div>
            </div>
        </div>
    );
}
