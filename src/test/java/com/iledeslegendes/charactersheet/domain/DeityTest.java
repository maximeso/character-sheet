package com.iledeslegendes.charactersheet.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.iledeslegendes.charactersheet.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deity.class);
        Deity deity1 = new Deity();
        deity1.setId(1L);
        Deity deity2 = new Deity();
        deity2.setId(deity1.getId());
        assertThat(deity1).isEqualTo(deity2);
        deity2.setId(2L);
        assertThat(deity1).isNotEqualTo(deity2);
        deity1.setId(null);
        assertThat(deity1).isNotEqualTo(deity2);
    }
}
