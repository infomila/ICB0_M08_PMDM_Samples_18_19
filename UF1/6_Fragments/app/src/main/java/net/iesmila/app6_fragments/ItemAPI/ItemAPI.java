package net.iesmila.app6_fragments.ItemAPI;

import net.iesmila.app6_fragments.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemAPI {


    @GET("items.json") // "http://localhost/items/
    Call<List<Item>> getItems();


}
