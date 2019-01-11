package com.travis.c196_project;

public class Term {

    private long termId;
    private String termStart;
    private String termEnd;
    private String termName;

    Term() {

    }

    @Override
    public String toString() {
        return termName + "\n" + termStart + " - " + termEnd;
    }

    long getTermId() {
        return termId;
    }

    void setTermId(long termId) {
        this.termId = termId;
    }

    String getTermStart() {
        return termStart;
    }

    void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    String getTermEnd() {
        return termEnd;
    }

    void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }





}
