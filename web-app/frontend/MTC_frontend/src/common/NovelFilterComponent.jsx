const FilterItem = ({ filter, onFilterChange }) => {
    const handleChange = (event) => {
        onFilterChange();
        console.log("Filter item changed:", filter, event.target.checked);
    };

    return (
        <div className="flex items-center p-2 rounded-sm hover:cursor-pointer">
            <input
                id={`status-${filter.id}`}
                type="checkbox"
                checked={filter.isOnFilter}
                onChange={handleChange}
                className="w-4 h-4 hidden peer"
            />
            <label
                htmlFor={`status-${filter.id}`}
                className={
                    `select-none cursor-pointer rounded border border-title text-title text-xs py-1 px-3 duration-200 ease-in-out ` +
                    (filter.isOnFilter
                        ? 'bg-yellow-primary text-white'
                        : '')
                }
            >
                {filter.name}
            </label>
        </div>
    );

}

export { FilterItem };