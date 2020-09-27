DROP VIEW IF EXISTS gwf_vw_workflow;

CREATE OR REPLACE VIEW gwf_vw_workflow AS
      SELECT gwpe.id,
        gwpe.project_type_id,
        gwpt.name AS project_type_name,
        gwpe.project_since,
        gwpe.project_until,
        gwpe.year,
        gwpe.period,
        gwpe.user_id,
        u.name AS user_name,
        gwpe.name,
        gwpe.gwf_client_id,
        gwc.business_name AS gwf_client_name,
        gwpe.project_advance,
        gwpe.project_id,
        gwpe.template_id,
        gwpe.is_order AS order_execution,
        gwpe.cliente_id,
        (SELECT COUNT(*)
         FROM gwf_activity qwa
         WHERE qwa.project_execution_id = gwpe.id
         AND qwa.comments = true) AS comments_number,
        (SELECT COUNT(*)
         FROM gwf_activity qwa
         WHERE qwa.project_execution_id = gwpe.id
         AND qwa.attached = true) AS attached_number,
         (SELECT COUNT(*)
         FROM gwf_activity qwa
         WHERE qwa.project_execution_id = gwpe.id
         AND qwa.status = 'EN_ESPERA_AUTORIZACION') AS pending_number,
        (SELECT COUNT(*)
         FROM gwf_activity qwa
         WHERE qwa.project_execution_id = gwpe.id
         AND qwa.status = 'RECHAZADA') AS rejected_number,
		 (SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 1) as activity1,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 1) as status1,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 1) as comment1,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 1) as attached1,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 2) as activity2,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 2) as status2,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 2) as comment2,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 2) as attached2,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 3) as activity3,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 3) as status3,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 3) as comment3,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 3) as attached3,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 4) as activity4,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 4) as status4,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 4) as comment4,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 4) as attached4,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 5) as activity5,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 5) as status5,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 5) as comment5,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 5) as attached5,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 6) as activity6,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 6) as status6,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 6) as comment6,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 6) as attached6,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 7) as activity7,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 7) as status7,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 7) as comment7,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 7) as attached7,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 8) as activity8,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 8) as status8,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 8) as comment8,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 8) as attached8,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 9) as activity9,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 9) as status9,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 9) as comment9,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 9) as attached9,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 10) as activity10,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 10) as status10,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 10) as comment10,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 10) as attached10,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 11) as activity11,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 11) as status11,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 11) as comment11,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 11) as attached11,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 12) as activity12,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 12) as status12,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 12) as comment12,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 12) as attached12,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 13) as activity13,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 13) as status13,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 13) as comment13,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 13) as attached13,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 14) as activity14,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 14) as status14,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 14) as comment14,
		(SELECT ac.attached FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.attached
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 14) as attached14,
		(SELECT ac.activity_id FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.id as activity_id
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 15) as activity15,
		(SELECT ac.status FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.status
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 15) as status15,
		(SELECT ac.comments FROM (
			SELECT ROW_NUMBER() over (order by qwa.activity_order) as id, qwa.comments
			FROM gwf_activity qwa
			WHERE project_execution_id = gwpe.id
		) as ac
		WHERE ac.id = 15) as comment15
    FROM gwf_project_execution gwpe
         JOIN gwf_project_type gwpt ON gwpe.project_type_id = gwpt.id
         JOIN gwf_user u ON gwpe.user_id = u.id
         JOIN gwf_client gwc ON gwpe.gwf_client_id = gwc.id
    WHERE CURRENT_DATE >= gwpe.project_since;