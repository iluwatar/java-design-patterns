import entity.Bowler;
import entity.Cricketer;
import entity.Footballer;
import entity.Player;
import lombok.extern.slf4j.Slf4j;
import mapper.player;
import org.apache.ibatis.session.SqlSession;

@Slf4j
public class App {
    public static void main(String[] args) {
        SqlSession sqlSession;
        player playerMapper;
        sqlSession = Mybatis3Utils.getCurrentSqlSession();
        playerMapper = sqlSession.getMapper(player.class);

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
}
