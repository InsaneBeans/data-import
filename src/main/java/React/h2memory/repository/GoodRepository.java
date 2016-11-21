package React.h2memory.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import React.h2memory.domain.Good;

@Transactional
public interface GoodRepository extends JpaRepository<Good, Long>{
}
