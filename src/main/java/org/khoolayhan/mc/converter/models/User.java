package org.khoolayhan.mc.converter.models;

public record User(int id, String name, String email) {
  public User() {}

  public User(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}
