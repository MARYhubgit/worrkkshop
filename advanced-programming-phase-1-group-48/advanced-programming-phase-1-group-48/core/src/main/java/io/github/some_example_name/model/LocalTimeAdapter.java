//package io.github.some_example_name.model;
//
//import com.google.gson.TypeAdapter;
//import com.google.gson.stream.JsonReader;
//import com.google.gson.stream.JsonWriter;
//
//import java.io.IOException;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
//
//    @Override
//    public void write(JsonWriter out, LocalTime value) throws IOException {
//        if (value == null) {
//            out.nullValue();
//            return;
//        }
//        out.value(value.format(formatter));
//    }
//
//    @Override
//    public LocalTime read(JsonReader in) throws IOException {
//        String timeString = in.nextString();
//        return LocalTime.parse(timeString, formatter);
//    }
//}
//
