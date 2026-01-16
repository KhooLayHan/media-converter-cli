package org.khoolayhan.mc.converter.models;

/**
 * Represents a user in the system.
 *
 * @param id the unique identifier of the user
 * @param name the full name of the user
 * @param email the email address of the user
 */
public record User(int id, String name, String email) {}
