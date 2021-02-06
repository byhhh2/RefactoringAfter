package Model;

import Default.GameManager;
import View.InputInfo;
import Database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoData {

    private ResultSet rs;
    private InputInfo inputInfo;
    private PreparedStatement pstmt;

    public InfoData(){
        Database.connection();
        inputInfo = GameManager.getGameManager().getInputInfo();
    }

    public ResultSet excuteQueryPstmt(String queryStmt) throws SQLException, ClassNotFoundException { //select문을 실행하기 위한 함수
        try {
            pstmt = Database.con.prepareStatement(queryStmt);
            pstmt.setString(1, inputInfo.userText.getText());
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("executeQuery fail");
            throw e;
        }
    }

    public void excuteUpdatePstmt(String sqlStmt, int stringNum, int intNum) throws SQLException, ClassNotFoundException { //insert문과 update문을 실행하기 위한 함수 
        try {
            pstmt = Database.con.prepareStatement(sqlStmt);
            pstmt.setString(stringNum, inputInfo.userText.getText());
            if (intNum == 1) pstmt.setInt(intNum, countOfWin()); //기존회원
            else pstmt.setInt(intNum, 1); //신규회원
            pstmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("executeUpdate fail");
            throw e;
        }
    }

    private int countOfWin() throws SQLException, ClassNotFoundException { //기존 회원의 승리 횟수에 이번 게임 승리 횟수를 더한 후 반환 
        int cnt = 0;

        try {
            while (rs.next()) cnt = rs.getInt(1);
            cnt++;
        } catch (SQLException e) {
            System.out.println("count of win fail");
            throw e;
        }

        return cnt;
    }

    public void inputInfoDisconnect() {
        Database.disConnection();
    }
}