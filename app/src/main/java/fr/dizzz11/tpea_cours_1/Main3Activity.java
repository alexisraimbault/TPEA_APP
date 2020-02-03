package fr.dizzz11.tpea_cours_1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

public class Main3Activity extends AppCompatActivity {

    public HashMap<String, String> images;
    public HashMap<String, String> descriptions;

    public ImageView image;
    public ImageView btnBack;
    public TextView description;

    public String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getSupportActionBar().hide();

        image = (ImageView) findViewById(R.id.image);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        description = (TextView) findViewById(R.id.description);

        images = new HashMap<String, String>();

        images.put("grass","https://images.ctfassets.net/ir9614n98s3l/6DnvoTZUUKYK1TKeTLvoRb/4c1177d2e6f4c08854826217d08d07c8/grass.png?h=250");
        images.put("tree","https://images.ctfassets.net/ir9614n98s3l/6OslzUJwa25A3wJ3GQiv6B/97ae379ee6d9577cab4b02976a389559/tree.png?h=250");
        images.put("insect","https://images.ctfassets.net/ir9614n98s3l/3nLeOI6diIn3eAdXqzGvQw/f5647f235fbe583a0d3d2cc80c8487a6/insect.png?h=250");
        images.put("ant","https://images.ctfassets.net/ir9614n98s3l/21GNq1lqTuW6MNntSwqBhX/812d02bfe2497e73184dca96ff89ca16/ant.png?h=250");
        images.put("flower","https://images.ctfassets.net/ir9614n98s3l/PUWOGp5sE0rZMcatICi16/06af722a5558dd9f9a7b1f01cebd3219/flower.png?h=250");
        images.put("animal","https://images.ctfassets.net/ir9614n98s3l/mSXdtR71kd4SARIGVlRxb/64403569de66726cc64e33cd0c90f555/animal.png?h=250");
        images.put("bird","https://images.ctfassets.net/ir9614n98s3l/7xwWPVgam1dvcEMa3q8SWc/dc6eaa2b91dbb66e64e448be014cfa15/bird.png?h=250");
        images.put("field","https://images.ctfassets.net/ir9614n98s3l/1uRY0wAZEzcqppWlC49RQM/b294d1e01320314c3402d0f2ee5c3e45/field.png?h=250");

        images.put("window","https://images.ctfassets.net/ir9614n98s3l/3kPMukRNPx01rDelNZhhyQ/d70d062940a5555a8b92802716453df6/window.png?h=250");
        images.put("glass","https://images.ctfassets.net/ir9614n98s3l/3lUJjEfqp3UMmjNlK4oSx2/24cdea5c32dcc4b86b1870133040241d/glass.png?h=250");
        images.put("street","https://images.ctfassets.net/ir9614n98s3l/4LQq5oadhDpogV4IVEDHq0/33c83552d78dfe0711f24c50c001a219/street.png?h=250");
        images.put("table","https://images.ctfassets.net/ir9614n98s3l/6jsYIKNrHlNP4gSOzHSCrA/48a559b9ff5665b5e50f75085712f13c/table.png?h=250");
        images.put("computer","https://images.ctfassets.net/ir9614n98s3l/2msstBP1kdZR0IfSMIx1D5/fd77156a3ec677176251708b6da0ca52/computer.png?h=250");
        images.put("screen","https://images.ctfassets.net/ir9614n98s3l/6gG028rZALzYvySLhN280S/2fe2a97aab3c42a544927ee25a120add/screen.png?h=250");
        images.put("knife","https://images.ctfassets.net/ir9614n98s3l/2s6OTO6YWt7fwo3TcIkVcL/10687252f1d6824991a43b9d789906a4/knife.png?h=250");
        images.put("plate","https://images.ctfassets.net/ir9614n98s3l/7FJ5dF5AilxXa2ryoRc5Zv/b4cdaa6cd85be6a5beb6d69b9bf84593/plate.png?h=250");
        images.put("mouse","https://images.ctfassets.net/ir9614n98s3l/1GfSyk2s3fgqj7ysUY3vtE/78874828175f86f67efdf59623d381ef/mouse.png?h=250");
        images.put("pen","https://images.ctfassets.net/ir9614n98s3l/7sfo5azfLW8BNQ6n8NZL0b/2f54cd35a78b1f82ea675eea691d1474/pen.png?h=250");
        images.put("pencil","https://images.ctfassets.net/ir9614n98s3l/3AuAqsfLeSl2KFrL5OvLKU/64f4ab5be6797cb7ab253d860a3c537b/pencil.png?h=250");
        images.put("keyboard","https://images.ctfassets.net/ir9614n98s3l/5oRAqIrm2ibzbpylmHUZvG/37ffb65466cf90355f7e4b37a171f0a4/keyboard.png?h=250");
        images.put("fork","https://images.ctfassets.net/ir9614n98s3l/1iNWb6HDO1941YZ1PkYK0k/d3cfc028105efcb6be149472d9fec2f5/fork.png?h=250");
        images.put("door","https://images.ctfassets.net/ir9614n98s3l/WQUlrLuhejcgORYfgfVgW/c42ccb53f4dc72ee5b86f5b9eff1bbeb/door.png?h=250");
        images.put("chair","https://images.ctfassets.net/ir9614n98s3l/5gmbz7zIbQHMMIzO6bOcWJ/4d70cbb5bcbea25ce6056adec83852c9/chair.png?h=250");
        images.put("desk","https://images.ctfassets.net/ir9614n98s3l/KYR5pbpmbNAFzffssDjgg/475d439a73dcdfc675bcb3ccac8d473c/desk.png?h=250");
        images.put("bowl","https://images.ctfassets.net/ir9614n98s3l/5xPYXUKTkjNrnEbOfRmO1I/480bc214a270f059acaede93342ca161/bowl.png?h=250");
        images.put("bottle","https://images.ctfassets.net/ir9614n98s3l/5vj9W1dwAcfP8b2R3TFFLB/733ec0a34b1c5686443c3c68928c8717/bottle.png?h=250");
        images.put("car","https://images.ctfassets.net/ir9614n98s3l/6blfe2cEZyoG7jH3neGYbT/fb4f615a8fe84efad76511138897c5d4/car.png?h=250");
        images.put("bag","https://images.ctfassets.net/ir9614n98s3l/7Cu3Yn55YSopLy1jNScwzq/853b6299cf2666dd9f24e12e9cdbfb85/bag.png?h=250");
        images.put("bike","https://images.ctfassets.net/ir9614n98s3l/3y7W40C1yBpyKgypZOxc2O/636f547ed5dd7ab49cf51a35313a9dbe/bike.png?h=250");

        descriptions = new HashMap<String, String>();

        descriptions.put("grass","Vegetation consisting of typically short plants with long, narrow leaves, growing wild or cultivated on lawns and pasture, and as a fodder crop.");
        descriptions.put("tree","A woody perennial plant, typically having a single stem or trunk growing to a considerable height and bearing lateral branches at some distance from the ground.");
        descriptions.put("insect","A small arthropod animal that has six legs and generally one or two pairs of wings.");
        descriptions.put("ant","A small insect typically having a sting and living in a complex social colony with one or more breeding queens. It is wingless except for fertile adults, which form large mating swarms, and is proverbial for its industriousness.");
        descriptions.put("flower","The seed-bearing part of a plant, consisting of reproductive organs (stamens and carpels) that are typically surrounded by a brightly coloured corolla (petals) and a green calyx (sepals).");
        descriptions.put("animal","A living organism that feeds on organic matter, typically having specialized sense organs and nervous system and able to respond rapidly to stimuli.");
        descriptions.put("bird","A warm-blooded egg-laying vertebrate animal distinguished by the possession of feathers, wings, a beak, and typically by being able to fly.");
        descriptions.put("field","An area of open land, especially one planted with crops or pasture, typically bounded by hedges or fences.");

        descriptions.put("window","an opening in the wall or roof of a building or vehicle, fitted with glass in a frame to admit light or air and allow people to see out.");
        descriptions.put("glass","a drinking container made from glass.");
        descriptions.put("street","a public road in a city, town, or village, typically with houses and buildings on one or both sides.");
        descriptions.put("table","a piece of furniture with a flat top and one or more legs, providing a level surface for eating, writing, or working at.");
        descriptions.put("computer","an electronic device for storing and processing data, typically in binary form, according to instructions given to it in a variable program.");
        descriptions.put("screen","a flat panel or area on an electronic device such as a television, computer, or smartphone, on which images and data are displayed.");
        descriptions.put("knife","an instrument composed of a blade fixed into a handle, used for cutting or as a weapon.");
        descriptions.put("plate","dishes, bowls, cups, and other utensils made of gold, silver, or other metal.");
        descriptions.put("mouse","a small handheld device which is moved across a mat or flat surface to move the cursor on a computer screen.");
        descriptions.put("pen","an instrument for writing or drawing with ink, typically consisting of a metal nib or ball, or a nylon tip, fitted into a metal or plastic holder.");
        descriptions.put("pencil","an instrument for writing or drawing, consisting of a thin stick of graphite or a similar substance enclosed in a long thin piece of wood or fixed in a cylindrical case.");
        descriptions.put("keyboard","a panel of keys that operate a computer or typewriter.");
        descriptions.put("fork","an implement with two or more prongs used for lifting food to the mouth or holding it when cutting.");
        descriptions.put("door","a hinged, sliding, or revolving barrier at the entrance to a building, room, or vehicle, or in the framework of a cupboard.");
        descriptions.put("chair","a separate seat for one person, typically with a back and four legs.");
        descriptions.put("desk","a piece of furniture with a flat or sloping surface and typically with drawers, at which one can read, write, or do other work.");
        descriptions.put("bowl","a round, deep dish or basin used for food or liquid.");
        descriptions.put("bottle","a glass or plastic container with a narrow neck, used for storing drinks or other liquids.");
        descriptions.put("car","a road vehicle, typically with four wheels, powered by an internal combustion engine and able to carry a small number of people.");
        descriptions.put("bag","a flexible container with an opening at the top, used for carrying things.");
        descriptions.put("bike","a bicycle or motorcycle.");

        Intent intent = getIntent();
        word = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);



        try {
            InputStream is = (InputStream) new URL(images.get(word)).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            image.setImageDrawable(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        description.setText(descriptions.get(word));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainActivity();
            }
        });
    }

    public void launchMainActivity()
    {
//        Intent intent = new Intent(this, Main2Activity.class);
//        intent.putExtra(MainActivity.EXTRA_MESSAGE, word);
//        startActivity(intent);
        this.finish();
    }
}
