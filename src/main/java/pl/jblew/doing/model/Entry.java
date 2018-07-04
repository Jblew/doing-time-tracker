package pl.jblew.doing.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class Entry {
    public String subproject = "";
    public String task = "";
    public String [] tags = {};
    public String comment= "";
    public Duration duration = Duration.ZERO;

    @JsonIgnore
    public LocalDateTime start = LocalDateTime.MIN;
    @JsonIgnore
    public LocalDateTime stop = LocalDateTime.MAX;

    @JsonGetter
    public String getStart() {
        return start.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonSetter
    public void setStart(String start) {
        this.start = LocalDateTime.parse(start, DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonGetter
    public String getStop() {
        return stop.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @JsonSetter
    public void setStop(String stop) {
        this.stop = LocalDateTime.parse(stop, DateTimeFormatter.ISO_DATE_TIME);
    }

    public Entry duplicate() {
        Entry e = new Entry();
        e.subproject = this.subproject;
        e.task = this.task;
        e.tags = Arrays.copyOf(this.tags, this.tags.length);
        e.comment = this.comment;
        return e;
    }
}
