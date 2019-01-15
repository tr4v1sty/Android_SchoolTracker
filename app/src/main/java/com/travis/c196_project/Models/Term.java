package com.travis.c196_project.Models;

public class Term {

    private long termId;
    private String termStart;
    private String termEnd;
    private String termName;

    public Term() {

    }

    @Override
    public String toString() {
        return termName + "\n" + termStart + " - " + termEnd;
    }

    public long getTermId() {
        return termId;
    }

    public void setTermId(long termId) {
        this.termId = termId;
    }

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }





}
