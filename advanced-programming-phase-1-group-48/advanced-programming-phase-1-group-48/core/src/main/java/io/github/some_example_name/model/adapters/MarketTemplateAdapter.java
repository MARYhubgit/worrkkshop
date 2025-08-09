//package io.github.some_example_name.model.adapters;
//
//import com.google.gson.*;
//import io.github.some_example_name.model.market.ClassicMarketTemplate;
//import io.github.some_example_name.model.market.MarketTemplate;
//
//
//import java.lang.reflect.Type;
//
//public class MarketTemplateAdapter implements JsonSerializer<MarketTemplate>, JsonDeserializer<MarketTemplate> {
//
//    @Override
//    public JsonElement serialize(MarketTemplate src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject obj = context.serialize(src).getAsJsonObject();
//        obj.addProperty("marketType", src.getClass().getSimpleName());
//        return obj;
//    }
//
//    @Override
//    public MarketTemplate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject obj = json.getAsJsonObject();
//        String type = obj.get("marketType").getAsString();
//
//        switch (type) {
//            case "ClassicMarketTemplate":
//                return context.deserialize(json, ClassicMarketTemplate.class);
//            default:
//                throw new JsonParseException("Unknown marketType: " + type);
//        }
//    }
//}
//
