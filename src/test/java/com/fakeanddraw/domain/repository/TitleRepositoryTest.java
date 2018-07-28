package com.fakeanddraw.domain.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.fakeanddraw.domain.model.MasterTitle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TitleRepositoryTest {

  @Autowired
  private TitleRepository titleRepository;

  @Test
  public void getMasterTitles() {
    List<MasterTitle> masterTitles = titleRepository.getMasterTitles(2);
    assertNotNull(masterTitles);
    assertTrue(!masterTitles.isEmpty());
    assertEquals(2, masterTitles.size());
  }
}
