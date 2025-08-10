export const getNovelUrlWithIdx = (novelSlug, chapterIdx) => {
    return `/truyen/${novelSlug}/chuong-${chapterIdx}`;
}

export const getNovelUrlWithSlug = (novelSlug) => {
    return `/truyen/${novelSlug}`;
}