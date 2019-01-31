package ru.configuration;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("tokenRepositoryDao")
@Transactional
public class HibernateTokenRepository extends AbstractDao{

}
