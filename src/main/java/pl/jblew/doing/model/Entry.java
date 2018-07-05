package pl.jblew.doing.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import pl.jblew.doing.StaticConfig;

import java.nio.charset.StandardCharsets;
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

    @JsonIgnore
    public LocalDateTime start = LocalDateTime.MIN;
    @JsonIgnore
    public LocalDateTime stop = LocalDateTime.MAX;

    @JsonGetter
    public String getStart() {
        return start.format(StaticConfig.DATETIME_FORMATTER);
    }

    @JsonSetter
    public void setStart(String start) {
        this.start = LocalDateTime.parse(start, StaticConfig.DATETIME_FORMATTER);
    }

    @JsonGetter
    public String getStop() {
        if (stop.isEqual(LocalDateTime.MAX)) return "";
        else return stop.format(StaticConfig.DATETIME_FORMATTER);
    }

    @JsonSetter
    public void setStop(String stop) {
        if (stop == null || stop.trim().isEmpty()) this.stop = LocalDateTime.MAX;
        else this.stop = LocalDateTime.parse(stop, StaticConfig.DATETIME_FORMATTER);
    }

    public Entry duplicate() {
        Entry e = new Entry();
        e.subproject = this.subproject;
        e.task = this.task;
        e.tags = Arrays.copyOf(this.tags, this.tags.length);
        e.comment = this.comment;
        return e;
    }

    @JsonIgnore
    public String getHumanFriendlyHash() {
        String tagsStr = Arrays.stream(tags).reduce("", (t1, t2) -> t1 + t2);
        return BaseEncoding.base32Hex().encode(Hashing.sha1().hashString(task+subproject+tagsStr, StandardCharsets.UTF_8).asBytes()).toLowerCase();
    }
}
