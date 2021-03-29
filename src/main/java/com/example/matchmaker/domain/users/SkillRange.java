package com.example.matchmaker.domain.users;

import java.util.Objects;

public class SkillRange {

    private final Double start;
    private final Double end;

    public SkillRange(Double start, Double end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(Double number) {
        return number.compareTo(start) >= 0 && number.compareTo(end) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillRange that = (SkillRange) o;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
