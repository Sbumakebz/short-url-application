package com.sibusiso.short_url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrlMapping, Integer> {
    @Query(value = "SELECT * FROM short_url_mapping url_mapping WHERE url_mapping.long_url=:longUrl", nativeQuery = true)
    public List<ShortUrlMapping> getShortUrlMappingByLongUrl(@Param("longUrl") String longUrl);

    @Query(value = "SELECT MAX(id) FROM short_url_mapping", nativeQuery = true)
    public int getLatestId();
}
