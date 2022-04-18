import entity.Bowler;
import entity.Cricketer;
import entity.Footballer;
import entity.Player;
import mapper.MapperPlayer;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JunitTest {
    SqlSession sqlSession;
    MapperPlayer playerMapper;

    @Before
    public void before() {
        sqlSession = Mybatis3Utils.getCurrentSqlSession();
        playerMapper = sqlSession.getMapper(MapperPlayer.class);

    }

    @After
    public void after() {
        Mybatis3Utils.closeCurrentSession();
    }

    @Test
    public void test_insert(){
        Player a=new Player();
        a.setName("player1");
        playerMapper.insert_player(a);
        sqlSession.commit();

        Footballer footballer=new Footballer();
        footballer.setClub("footballerclub");
        footballer.setName("footballer1");
        playerMapper.insert_footballer(footballer);
        sqlSession.commit();

        Bowler bowler=new Bowler();
        bowler.setBowlingAvarage(23);
        bowler.setName("bowler1");
        playerMapper.insert_Bowler(bowler );
        sqlSession.commit();

        Cricketer cricketer=new Cricketer();
        cricketer.setName("cricketer1");
        cricketer.setBattingAvarage(23);
        playerMapper.insert_Cricketer(cricketer);
        sqlSession.commit();
    }
    @Test
    public void test_select(){

        playerMapper.list_player().forEach(x->{
            System.out.println(x.getName());
        });
        playerMapper.list_football_player().forEach(x->{
            System.out.println(x.getName()+' '+x.getClub());
        });
        playerMapper.list_cricketer().forEach(x->{
            System.out.println(x.getName()+" "+x.getBattingAvarage());
        });
        playerMapper.list_bowler().forEach(x->{
            System.out.println(x.getName()+" "+x.getBattingAvarage()+' '+x.getBowlingAvarage());
        });
    }
    @Test
    public void testupdate(){
        Bowler bowler=new Bowler();
        bowler.setName("bowler1");bowler.setBattingAvarage(11);bowler.setBowlingAvarage(16);
    playerMapper.update_Bowler(bowler);

    Cricketer cricketer=new Cricketer();
    cricketer.setName("cricketer1");cricketer.setBattingAvarage(3);
    playerMapper.update_Cricketer(cricketer);

    Footballer footballer=new Footballer();
    footballer.setName("footballer1");footballer.setClub("zzz");
    playerMapper.update_footballer(footballer);
    sqlSession.commit();
    }

    @Test
public void testdelete(){
    playerMapper.delete_player("player1");
    playerMapper.delete_Bowler(16);
    playerMapper.delete_Cricketer(3);
    playerMapper.delete_footballer("footballer1");
    sqlSession.commit();
    }
}
