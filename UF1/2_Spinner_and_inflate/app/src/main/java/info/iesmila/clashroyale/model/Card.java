package info.iesmila.clashroyale.model;

import java.util.ArrayList;
import java.util.List;

import info.iesmila.clashroyale.R;

public class Card
{
    Rarity rarity;
    int id;
    String name;
    int drawable;
    String desc;    
    int elixirCost;
    boolean selected;

    public Card(int id,  String nom, Rarity raresa, int drawable, String desc, int elixirCost) {
        this.rarity = raresa;
        this.id = id;
        this.name = nom;
        this.drawable = drawable;
        this.desc = desc;
        this.elixirCost = elixirCost;
    }
    
    private static ArrayList<Card> _cartes ;
    
    
    
    public static List<Card> getCartes(){

        if(_cartes==null) {
                _cartes = new ArrayList<Card>();
                _cartes.add(new Card(1, "Prince", Rarity.EPIC, R.drawable.prince, 
                        "Don't let the little pony fool you. Once the Prince gets a running start, you WILL be trampled. Deals double damage once he gets charging. ",
                        5 ));
                _cartes.add(new Card(2, "Skeleton Army", Rarity.RARE, R.drawable.skeletons,
                        " Spawns an army of Skeletons. Meet Larry and his friends Harry, Gerry, Terry, Mary, etc. ",
                        3 ));
                _cartes.add(new Card(3, "Giant", Rarity.RARE, R.drawable.giant,
                        " Slow but durable, only attacks buildings. A real one-man wrecking crew!  ",
                        5 ));
                _cartes.add(new Card(4, "Spear Goblins", Rarity.COMMON, R.drawable.spear_goblins,
                        " Three unarmored ranged attackers. Who the heck taught these guys to throw spears!? Who thought that was a good idea?!   ",
                        2 ));
        }
        
        return _cartes;
        
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getElixirCost() {
        return elixirCost;
    }

    public void setElixirCost(int elixirCost) {
        this.elixirCost = elixirCost;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void switchSelected() {
        this.selected = !selected;
    }
}