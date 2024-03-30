package com.sociate.sociate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sociate.sociate.models.Story;

public interface StoriesRepository extends JpaRepository<Story, Long> {

}
