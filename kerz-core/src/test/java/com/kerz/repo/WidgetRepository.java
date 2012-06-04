package com.kerz.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.kerz.domain.Widget;

public interface WidgetRepository extends JpaRepository<Widget, Long>, JpaSpecificationExecutor<Widget>
{
	List<Widget> findByName(String name);
	
	List<Widget> findByNameLike(String name);
}
