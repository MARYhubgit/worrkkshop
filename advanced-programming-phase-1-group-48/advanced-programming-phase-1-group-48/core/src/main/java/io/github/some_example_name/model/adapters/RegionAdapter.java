//package io.github.some_example_name.model.adapters;
//
//import com.google.gson.*;
//import io.github.some_example_name.model.farm.FarmRegion;
//import io.github.some_example_name.model.game.Region;
//import io.github.some_example_name.model.region.MarketRegion;
//import io.github.some_example_name.model.region.VillageRegion;
//
//
//import java.lang.reflect.Type;
//
//public class RegionAdapter implements JsonDeserializer<Region>, JsonSerializer<Region> {
//
//    @Override
//    public JsonElement serialize(Region src, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject obj = context.serialize(src).getAsJsonObject();
//
//        if (src instanceof FarmRegion) obj.addProperty("regionType", "farm");
//        else if (src instanceof MarketRegion) obj.addProperty("regionType", "market");
//        else if (src instanceof VillageRegion) obj.addProperty("regionType", "village");
//
//        return obj;
//    }
//
//    @Override
//    public Region deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject obj = json.getAsJsonObject();
//        String regionType = obj.get("regionType").getAsString();
//
//        switch (regionType) {
//            case "farm":
//                return context.deserialize(json, FarmRegion.class);
//            case "market":
//                return context.deserialize(json, MarketRegion.class);
//            case "village":
//                return context.deserialize(json, VillageRegion.class);
//            default:
//                throw new JsonParseException("Unknown regionType: " + regionType);
//        }
//    }
//}
