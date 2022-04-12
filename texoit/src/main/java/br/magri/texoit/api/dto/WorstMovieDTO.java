package br.magri.texoit.api.dto;

import java.util.List;

public class WorstMovieDTO {

    private List<WinningProducerDTO> min;
    private List<WinningProducerDTO> max;

    public List<WinningProducerDTO> getMin() {
        return min;
    }

    public void setMin(List<WinningProducerDTO> min) {
        this.min = min;
    }

    public List<WinningProducerDTO> getMax() {
        return max;
    }

    public void setMax(List<WinningProducerDTO> max) {
        this.max = max;
    }
}
