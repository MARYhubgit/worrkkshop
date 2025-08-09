//package io.github.some_example_name.model.adapters;
//
////import com.google.gson.JsonDeserializer;
////import com.google.gson.JsonSerializer;
////import com.google.gson.JsonElement;
////import com.google.gson.JsonSerializationContext;
////import com.google.gson.JsonDeserializationContext;
////import com.google.gson.JsonParseException;
////import com.google.gson.JsonObject;
//
//import java.lang.reflect.Type;
//
//import io.github.some_example_name.model.farm.DefaultFarmTemplate;
//import io.github.some_example_name.model.farm.FarmTemplate;
//import io.github.some_example_name.model.farm.LakeFarmTemplate;
//
//import io.github.some_example_name.model.farm.DefaultFarmTemplate;
//import io.github.some_example_name.model.farm.FarmTemplate;
//import io.github.some_example_name.model.farm.LakeFarmTemplate;
//
//
//import java.lang.reflect.Type;
//
//public class FarmTemplateAdapter implements JsonDeserializer<FarmTemplate>, JsonSerializer<FarmTemplate> {
//
//    @Override
//    public JsonElement serialize(FarmTemplate src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject obj = context.serialize(src).getAsJsonObject();
//        obj.addProperty("type", src.getClass().getSimpleName());
//        return obj;
//    }
//
//    @Override
//    public FarmTemplate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject obj = json.getAsJsonObject();
//        String type = obj.get("type").getAsString();
//
//        switch (type) {
//            case "LakeFarmTemplate":
//                return context.deserialize(obj, LakeFarmTemplate.class);
//            case "DefaultFarmTemplate":
//                return context.deserialize(obj, DefaultFarmTemplate.class);
//            default:
//                throw new JsonParseException("Unknown farm template type: " + type);
//        }
//    }
//}
