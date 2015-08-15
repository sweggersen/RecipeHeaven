package no.recipeheaven.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Sam Mathias Weggersen on 14/08/15.
 */
public class Recipe {
    public long id;
    public String title;
    public String description;
    public String status;
    public int basicPortionNumber;
    public int numberOfComments;
    public int numberOfLikes;
    public int preparationTime;
    public Date updatedAt;
    public Date publishedAt;

    @SerializedName("image")
    public String imageUrl;
}
