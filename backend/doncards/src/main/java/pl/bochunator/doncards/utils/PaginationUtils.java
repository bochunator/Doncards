package pl.bochunator.doncards.utils;

public class PaginationUtils {

    public static int getValidPageNumber(Integer page) {
        return (page != null && page >= 0) ? page : 0;
    }
}
