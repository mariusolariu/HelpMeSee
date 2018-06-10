package com.example.marius.helpmesee.location.model;

import java.util.List;

/**
 * Created by Marius Olariu <mariuslucian.olariu@gmail.com>
 */
public interface LocationModelManager {
  void addContact(String contactName, String phoneNumber);

  List<Contact> getContacts();
}
