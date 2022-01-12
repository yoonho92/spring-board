package test.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Setter
@Getter
public class Pagination {
    public Pagination(Page page, int currentPage) {
        this.totalPages = page.getTotalPages();
        this.currentPage = currentPage;
        this.isFirstBlock = currentPage < 10;
        this.isLastBlock = (page.getTotalPages() - 1) / 10 == currentPage / 10;
        this.startPage = (currentPage / 10) * 10 + 1;
        if((page.getTotalPages() - 1) / 10 == currentPage / 10){
            if(page.getTotalPages() == 0){
                this.endPage = page.getTotalPages() + 1;
            }
            else this.endPage = page.getTotalPages();

        }else{
            this.endPage = 10;
        }
    }

    private boolean isFirstBlock;
    private boolean isLastBlock;
    private int totalPages;
    private int currentPage;
    private int startPage;
    private int endPage;

}
