package dev.weriton.patterns.ch02.movefield;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum das bandeiras tarifárias do táxi.
 *
 * <p>Decisão além do livro: os {@code float} soltos {@code BANDEIRA_UM = 1.2f}
 * e {@code BANDEIRA_DOIS = 1.8f} foram substituídos por este enum. Os valores
 * ficam encapsulados no próprio enum via {@code @Getter} do Lombok, eliminando
 * números mágicos e protegendo contra valores inválidos — só existem
 * {@code BANDEIRA_1} e {@code BANDEIRA_2}.
 */
@Getter
@RequiredArgsConstructor
public enum Bandeira {
    BANDEIRA_1(1.2f),
    BANDEIRA_2(1.8f);

    private final float valor;
}
