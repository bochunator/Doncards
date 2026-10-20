import './DeckSort.css'

export type DeckSortOption = "LATEST" | "POPULAR";

type Props = {
    sort: DeckSortOption;
    setSort: (sort: DeckSortOption) => void;
}

function DeckSort({ sort, setSort}: Props) {
    return (
        <div className="deck-sort">
            <button className={`deck-sort__option ${sort === "POPULAR" ? "deck-sort__option--active" : ""
                }`}
                onClick={() => setSort("POPULAR")}
                aria-pressed={sort === "POPULAR"}>Popular</button>

            <button className={`deck-sort__option ${sort === "LATEST" ? "deck-sort__option--active" : ""
                }`}
                onClick={() => setSort("LATEST")}
                aria-pressed={sort === "LATEST"}>Newest</button>
        </div>
    )
}

export default DeckSort;