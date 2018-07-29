package com.fakeanddraw.domain.repository;

import java.util.List;
import com.fakeanddraw.domain.model.MasterTitle;

public interface TitleRepository {

  List<MasterTitle> getMasterTitles(int numTitles);
}
