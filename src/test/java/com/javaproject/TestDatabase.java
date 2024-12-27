package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.javaproject.beans.BoardBase;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class TestDatabase {

    private DatabaseAccess da;

    @Autowired
    public void setDatabase(DatabaseAccess da) {
        this.da = da;
    }

    @Test
    public void testDatabaseAddBoardBase() {
        BoardBase boardBase = new BoardBase();
        boardBase.setName("onecard");
        boardBase.setLevel(1);
        boardBase.setMinPlayers(2);
        boardBase.setMaxPlayers("+");
        boardBase.setGameType("Party Game");

        int originalSize = da.getBoardBases().size();

        da.addBoardBase(BoardBase);
        int newSize = da.getBoardBases().size();

        assertEquals(newSize, originalSize + 1);
    }

    
}
