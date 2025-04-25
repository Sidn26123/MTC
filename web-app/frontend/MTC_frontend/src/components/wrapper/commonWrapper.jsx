const ScrollableWrapper = ({ height = "75vh", children }) => {
    return (
        <div className={`overflow-y-auto`} style={{ maxHeight: height }}>
            {children}
        </div>
    );
};

export { ScrollableWrapper };