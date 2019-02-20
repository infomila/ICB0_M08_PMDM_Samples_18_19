package net.iesmila.a4_json_pokemon.pokeapi;

public class Pokemon
{
    public String name;
    public String url;

    @Override
    public String toString() {
        return "" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '\n';
    }
}
